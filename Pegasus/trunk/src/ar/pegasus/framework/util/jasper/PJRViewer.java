/*
 * ============================================================================
 * ============================================================================
 *
 * Copyright (C) 2001-2005 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
/*
 * Contributors:
 * Ryan Johnson - delscovich@users.sourceforge.net
 * Carlton Moore - cmoore79@users.sourceforge.net
 */
package ar.pegasus.framework.util.jasper;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRPrintAnchorIndex;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRPrintXmlLoader;
import net.sf.jasperreports.view.JRHyperlinkListener;
import net.sf.jasperreports.view.JRSaveContributor;
import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PException;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRViewer.java,v 1.33 2005/11/28 09:20:45 teodord Exp $
 */
public class PJRViewer extends javax.swing.JPanel implements JRHyperlinkListener {
	private static final long serialVersionUID = 3235296088583016409L;

	private static final int TYPE_FILE_NAME = 1;
	private static final int TYPE_INPUT_STREAM = 2;
	private static final int TYPE_JASPER_PRINT = 3;
	/**
	 * The DPI of the generated report.
	 */
	public static final int REPORT_RESOLUTION = 72;
	protected float MIN_ZOOM = 0.5f;
	protected float MAX_ZOOM = 2.5f;
	protected int zooms[] = { 50, 75, 100, 125, 150, 175, 200, 250 };
	protected int defaultZoomIndex = 2;
	private int type = TYPE_FILE_NAME;
	private boolean isXML = false;
	private String reportFileName = null;
	JasperPrint jasperPrint = null;
	private int pageIndex = 0;
	private float zoom = 0f;
	/**
	 * the screen resolution.
	 */
	private int screenResolution = REPORT_RESOLUTION;
	/**
	 * the zoom ration adjusted to the screen resolution.
	 */
	private float realZoom = 0f;
	private DecimalFormat zoomDecimalFormat = new DecimalFormat("#.##");
	private int downX = 0;
	private int downY = 0;
	private java.util.List hyperlinkListeners = new ArrayList();
	private Map linksMap = new HashMap();
	private MouseListener mouseListener = new java.awt.event.MouseAdapter() {

		public void mouseClicked(java.awt.event.MouseEvent evt) {
			hyperlinkClicked(evt);
		}
	};

	/** Creates new form JRViewer */
	public PJRViewer(String fileName, boolean isXML) throws JRException {
		setScreenDetails();
		setZooms();
		initComponents();
		loadReport(fileName, isXML);
		cmbZoom.setSelectedIndex(defaultZoomIndex);
		addHyperlinkListener(this);
	}

	/** Creates new form JRViewer */
	public PJRViewer(InputStream is, boolean isXML) throws JRException {
		setScreenDetails();
		setZooms();
		initComponents();
		loadReport(is, isXML);
		cmbZoom.setSelectedIndex(defaultZoomIndex);
		addHyperlinkListener(this);
	}

	/** Creates new form JRViewer */
	public PJRViewer(JasperPrint jrPrint) {
		setScreenDetails();
		setZooms();
		initComponents();
		loadReport(jrPrint);
		cmbZoom.setSelectedIndex(defaultZoomIndex);
		addHyperlinkListener(this);
	}

	private void setScreenDetails() {
		screenResolution = Toolkit.getDefaultToolkit().getScreenResolution();
	}

	public void clear() {
		emptyContainer(this);
		jasperPrint = null;
	}

	protected void setZooms() {
	}

	@SuppressWarnings("unchecked")
	public void addHyperlinkListener(JRHyperlinkListener listener) {
		hyperlinkListeners.add(listener);
	}

	public void removeHyperlinkListener(JRHyperlinkListener listener) {
		hyperlinkListeners.remove(listener);
	}

	@SuppressWarnings("unchecked")
	public JRHyperlinkListener[] getHyperlinkListeners() {
		return (JRHyperlinkListener[])hyperlinkListeners.toArray(new JRHyperlinkListener[hyperlinkListeners.size()]);
	}

	public void gotoHyperlink(JRPrintHyperlink hyperlink) {
		switch(hyperlink.getHyperlinkType()) {
			case JRHyperlink.HYPERLINK_TYPE_REFERENCE: {
				if(hyperlinkListeners != null && hyperlinkListeners.size() > 1) {
					System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_ANCHOR: {
				if(hyperlink.getHyperlinkAnchor() != null) {
					Map anchorIndexes = jasperPrint.getAnchorIndexes();
					JRPrintAnchorIndex anchorIndex = (JRPrintAnchorIndex)anchorIndexes.get(hyperlink.getHyperlinkAnchor());
					if(anchorIndex.getPageIndex() != pageIndex) {
						setPageIndex(anchorIndex.getPageIndex());
						refreshPage();
					}
					Container container = pnlInScroll.getParent();
					if(container instanceof JViewport) {
						JViewport viewport = (JViewport)container;
						int newX = (int)(anchorIndex.getElement().getX() * realZoom);
						int newY = (int)(anchorIndex.getElement().getY() * realZoom);
						int maxX = pnlInScroll.getWidth() - viewport.getWidth();
						int maxY = pnlInScroll.getHeight() - viewport.getHeight();
						if(newX < 0) {
							newX = 0;
						}
						if(newX > maxX) {
							newX = maxX;
						}
						if(newY < 0) {
							newY = 0;
						}
						if(newY > maxY) {
							newY = maxY;
						}
						viewport.setViewPosition(new Point(newX, newY));
					}
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_PAGE: {
				int page = pageIndex + 1;
				if(hyperlink.getHyperlinkPage() != null) {
					page = hyperlink.getHyperlinkPage().intValue();
				}
				if(page >= 1 && page <= jasperPrint.getPages().size() && page != pageIndex + 1) {
					setPageIndex(page - 1);
					refreshPage();
					Container container = pnlInScroll.getParent();
					if(container instanceof JViewport) {
						JViewport viewport = (JViewport)container;
						viewport.setViewPosition(new Point(0, 0));
					}
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_REMOTE_ANCHOR: {
				if(hyperlinkListeners != null && hyperlinkListeners.size() > 1) {
					System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
					System.out.println("Hyperlink anchor    : " + hyperlink.getHyperlinkAnchor());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_REMOTE_PAGE: {
				if(hyperlinkListeners != null && hyperlinkListeners.size() > 1) {
					System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
					System.out.println("Hyperlink page      : " + hyperlink.getHyperlinkPage());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_NONE:
			default: {
				break;
			}
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {//GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;
		tlbToolBar = new javax.swing.JPanel();
		btnSave = new javax.swing.JButton();
		btnPrint = new javax.swing.JButton();
		btnReload = new javax.swing.JButton();
		pnlSep01 = new javax.swing.JPanel();
		btnFirst = new javax.swing.JButton();
		btnPrevious = new javax.swing.JButton();
		btnNext = new javax.swing.JButton();
		btnLast = new javax.swing.JButton();
		txtGoTo = new javax.swing.JTextField();
		pnlSep02 = new javax.swing.JPanel();
		btnActualSize = new javax.swing.JToggleButton();
		btnFitPage = new javax.swing.JToggleButton();
		btnFitWidth = new javax.swing.JToggleButton();
		pnlSep03 = new javax.swing.JPanel();
		btnZoomIn = new javax.swing.JButton();
		btnZoomOut = new javax.swing.JButton();
		cmbZoom = new javax.swing.JComboBox();
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(int i = 0; i < zooms.length; i++) {
			model.addElement("" + zooms[i] + "%");
		}
		cmbZoom.setModel(model);
		pnlMain = new javax.swing.JPanel();
		scrollPane = new javax.swing.JScrollPane();
		scrollPane.getHorizontalScrollBar().setUnitIncrement(5);
		scrollPane.getVerticalScrollBar().setUnitIncrement(5);
		pnlInScroll = new javax.swing.JPanel();
		pnlPage = new javax.swing.JPanel();
		jPanel4 = new javax.swing.JPanel();
		pnlLinks = new javax.swing.JPanel();
		jPanel5 = new javax.swing.JPanel();
		jPanel6 = new javax.swing.JPanel();
		jPanel7 = new javax.swing.JPanel();
		jPanel8 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jPanel9 = new javax.swing.JPanel();
		lblPage = new javax.swing.JLabel();
		pnlStatus = new javax.swing.JPanel();
		lblStatus = new javax.swing.JLabel();
		setLayout(new java.awt.BorderLayout());
		setMinimumSize(new java.awt.Dimension(450, 150));
		setPreferredSize(new java.awt.Dimension(450, 150));
		tlbToolBar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 2));
		btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/save.GIF")));
		btnSave.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("save"));
		btnSave.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnSave.setMaximumSize(new java.awt.Dimension(23, 23));
		btnSave.setMinimumSize(new java.awt.Dimension(23, 23));
		btnSave.setPreferredSize(new java.awt.Dimension(23, 23));
		btnSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSaveActionPerformed();
			}
		});
		tlbToolBar.add(btnSave);
		btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/print.GIF")));
		btnPrint.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("print"));
		btnPrint.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnPrint.setMaximumSize(new java.awt.Dimension(23, 23));
		btnPrint.setMinimumSize(new java.awt.Dimension(23, 23));
		btnPrint.setPreferredSize(new java.awt.Dimension(23, 23));
		btnPrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPrintActionPerformed();
			}
		});
		tlbToolBar.add(btnPrint);
		btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/reload.GIF")));
		btnReload.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("reload"));
		btnReload.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnReload.setMaximumSize(new java.awt.Dimension(23, 23));
		btnReload.setMinimumSize(new java.awt.Dimension(23, 23));
		btnReload.setPreferredSize(new java.awt.Dimension(23, 23));
		btnReload.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnReloadActionPerformed();
			}
		});
		tlbToolBar.add(btnReload);
		pnlSep01.setMaximumSize(new java.awt.Dimension(10, 10));
		tlbToolBar.add(pnlSep01);
		btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/first.GIF")));
		btnFirst.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("first.page"));
		btnFirst.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnFirst.setMaximumSize(new java.awt.Dimension(23, 23));
		btnFirst.setMinimumSize(new java.awt.Dimension(23, 23));
		btnFirst.setPreferredSize(new java.awt.Dimension(23, 23));
		btnFirst.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFirstActionPerformed();
			}
		});
		tlbToolBar.add(btnFirst);
		btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/previous.GIF")));
		btnPrevious.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("previous.page"));
		btnPrevious.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnPrevious.setMaximumSize(new java.awt.Dimension(23, 23));
		btnPrevious.setMinimumSize(new java.awt.Dimension(23, 23));
		btnPrevious.setPreferredSize(new java.awt.Dimension(23, 23));
		btnPrevious.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPreviousActionPerformed();
			}
		});
		tlbToolBar.add(btnPrevious);
		btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/next.GIF")));
		btnNext.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("next.page"));
		btnNext.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnNext.setMaximumSize(new java.awt.Dimension(23, 23));
		btnNext.setMinimumSize(new java.awt.Dimension(23, 23));
		btnNext.setPreferredSize(new java.awt.Dimension(23, 23));
		btnNext.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNextActionPerformed();
			}
		});
		tlbToolBar.add(btnNext);
		btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/last.GIF")));
		btnLast.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("last.page"));
		btnLast.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnLast.setMaximumSize(new java.awt.Dimension(23, 23));
		btnLast.setMinimumSize(new java.awt.Dimension(23, 23));
		btnLast.setPreferredSize(new java.awt.Dimension(23, 23));
		btnLast.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLastActionPerformed();
			}
		});
		tlbToolBar.add(btnLast);
		txtGoTo.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("go.to.page"));
		txtGoTo.setMaximumSize(new java.awt.Dimension(40, 23));
		txtGoTo.setMinimumSize(new java.awt.Dimension(40, 23));
		txtGoTo.setPreferredSize(new java.awt.Dimension(40, 23));
		txtGoTo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtGoToActionPerformed();
			}
		});
		tlbToolBar.add(txtGoTo);
		pnlSep02.setMaximumSize(new java.awt.Dimension(10, 10));
		tlbToolBar.add(pnlSep02);
		btnActualSize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/actualsize.GIF")));
		btnActualSize.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("actual.size"));
		btnActualSize.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnActualSize.setMaximumSize(new java.awt.Dimension(23, 23));
		btnActualSize.setMinimumSize(new java.awt.Dimension(23, 23));
		btnActualSize.setPreferredSize(new java.awt.Dimension(23, 23));
		btnActualSize.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnActualSizeActionPerformed();
			}
		});
		tlbToolBar.add(btnActualSize);
		btnFitPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/fitpage.GIF")));
		btnFitPage.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("fit.page"));
		btnFitPage.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnFitPage.setMaximumSize(new java.awt.Dimension(23, 23));
		btnFitPage.setMinimumSize(new java.awt.Dimension(23, 23));
		btnFitPage.setPreferredSize(new java.awt.Dimension(23, 23));
		btnFitPage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFitPageActionPerformed();
			}
		});
		tlbToolBar.add(btnFitPage);
		btnFitWidth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/fitwidth.GIF")));
		btnFitWidth.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("fit.width"));
		btnFitWidth.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnFitWidth.setMaximumSize(new java.awt.Dimension(23, 23));
		btnFitWidth.setMinimumSize(new java.awt.Dimension(23, 23));
		btnFitWidth.setPreferredSize(new java.awt.Dimension(23, 23));
		btnFitWidth.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFitWidthActionPerformed();
			}
		});
		tlbToolBar.add(btnFitWidth);
		pnlSep03.setMaximumSize(new java.awt.Dimension(10, 10));
		tlbToolBar.add(pnlSep03);
		btnZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/zoomin.GIF")));
		btnZoomIn.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("zoom.in"));
		btnZoomIn.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnZoomIn.setMaximumSize(new java.awt.Dimension(23, 23));
		btnZoomIn.setMinimumSize(new java.awt.Dimension(23, 23));
		btnZoomIn.setPreferredSize(new java.awt.Dimension(23, 23));
		btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnZoomInActionPerformed();
			}
		});
		tlbToolBar.add(btnZoomIn);
		btnZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/zoomout.GIF")));
		btnZoomOut.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("zoom.out"));
		btnZoomOut.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnZoomOut.setMaximumSize(new java.awt.Dimension(23, 23));
		btnZoomOut.setMinimumSize(new java.awt.Dimension(23, 23));
		btnZoomOut.setPreferredSize(new java.awt.Dimension(23, 23));
		btnZoomOut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnZoomOutActionPerformed();
			}
		});
		tlbToolBar.add(btnZoomOut);
		cmbZoom.setEditable(true);
		cmbZoom.setToolTipText(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("zoom.ratio"));
		cmbZoom.setMaximumSize(new java.awt.Dimension(80, 23));
		cmbZoom.setMinimumSize(new java.awt.Dimension(80, 23));
		cmbZoom.setPreferredSize(new java.awt.Dimension(80, 23));
		cmbZoom.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbZoomActionPerformed();
			}
		});
		cmbZoom.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cmbZoomItemStateChanged();
			}
		});
		tlbToolBar.add(cmbZoom);
		add(tlbToolBar, java.awt.BorderLayout.NORTH);
		pnlMain.setLayout(new java.awt.BorderLayout());
		pnlMain.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				pnlMainComponentResized();
			}
		});
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlInScroll.setLayout(new java.awt.GridBagLayout());
		pnlPage.setLayout(new java.awt.BorderLayout());
		pnlPage.setMinimumSize(new java.awt.Dimension(100, 100));
		pnlPage.setPreferredSize(new java.awt.Dimension(100, 100));
		jPanel4.setLayout(new java.awt.GridBagLayout());
		jPanel4.setMinimumSize(new java.awt.Dimension(100, 120));
		jPanel4.setPreferredSize(new java.awt.Dimension(100, 120));
		pnlLinks.setLayout(null);
		pnlLinks.setMinimumSize(new java.awt.Dimension(5, 5));
		pnlLinks.setPreferredSize(new java.awt.Dimension(5, 5));
		pnlLinks.setOpaque(false);
		pnlLinks.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				pnlLinksMousePressed(evt);
			}

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				pnlLinksMouseReleased();
			}
		});
		pnlLinks.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent evt) {
				pnlLinksMouseDragged(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel4.add(pnlLinks, gridBagConstraints);
		jPanel5.setBackground(java.awt.Color.gray);
		jPanel5.setMinimumSize(new java.awt.Dimension(5, 5));
		jPanel5.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		jPanel4.add(jPanel5, gridBagConstraints);
		jPanel6.setMinimumSize(new java.awt.Dimension(5, 5));
		jPanel6.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		jPanel4.add(jPanel6, gridBagConstraints);
		jPanel7.setBackground(java.awt.Color.gray);
		jPanel7.setMinimumSize(new java.awt.Dimension(5, 5));
		jPanel7.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel4.add(jPanel7, gridBagConstraints);
		jPanel8.setBackground(java.awt.Color.gray);
		jPanel8.setMinimumSize(new java.awt.Dimension(5, 5));
		jPanel8.setPreferredSize(new java.awt.Dimension(5, 5));
		jLabel1.setText("jLabel1");
		jPanel8.add(jLabel1);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		jPanel4.add(jPanel8, gridBagConstraints);
		jPanel9.setMinimumSize(new java.awt.Dimension(5, 5));
		jPanel9.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		jPanel4.add(jPanel9, gridBagConstraints);
		lblPage.setBackground(java.awt.Color.white);
		lblPage.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		lblPage.setOpaque(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanel4.add(lblPage, gridBagConstraints);
		pnlPage.add(jPanel4, java.awt.BorderLayout.CENTER);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		pnlInScroll.add(pnlPage, gridBagConstraints);
		scrollPane.setViewportView(pnlInScroll);
		pnlMain.add(scrollPane, java.awt.BorderLayout.CENTER);
		add(pnlMain, java.awt.BorderLayout.CENTER);
		pnlStatus.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
		lblStatus.setFont(new java.awt.Font("Dialog", 1, 10));
		lblStatus.setText("Page i of n");
		pnlStatus.add(lblStatus);
		add(pnlStatus, java.awt.BorderLayout.SOUTH);
	} //GEN-END:initComponents

	void txtGoToActionPerformed() { //GEN-FIRST:event_txtGoToActionPerformed
		try {
			int pageNumber = Integer.parseInt(txtGoTo.getText());
			if(pageNumber != pageIndex + 1 && pageNumber > 0 && pageNumber <= jasperPrint.getPages().size()) {
				setPageIndex(pageNumber - 1);
				refreshPage();
			}
		} catch(NumberFormatException e) {
		}
	} //GEN-LAST:event_txtGoToActionPerformed

	void cmbZoomItemStateChanged() { //GEN-FIRST:event_cmbZoomItemStateChanged
		//Add your handling code here:
		btnActualSize.setSelected(false);
		btnFitPage.setSelected(false);
		btnFitWidth.setSelected(false);
	} //GEN-LAST:event_cmbZoomItemStateChanged

	void pnlMainComponentResized() { //GEN-FIRST:event_pnlMainComponentResized
		//Add your handling code here:
		if(btnFitPage.isSelected()) {
			setRealZoomRatio(((float)pnlInScroll.getVisibleRect().getHeight() - 20f) / jasperPrint.getPageHeight());
		} else if(btnFitWidth.isSelected()) {
			setRealZoomRatio(((float)pnlInScroll.getVisibleRect().getWidth() - 20f) / jasperPrint.getPageWidth());
		}
	} //GEN-LAST:event_pnlMainComponentResized

	void btnActualSizeActionPerformed() { //GEN-FIRST:event_btnActualSizeActionPerformed
		//Add your handling code here:
		if(btnActualSize.isSelected()) {
			btnFitPage.setSelected(false);
			btnFitWidth.setSelected(false);
			setZoomRatio(1);
		}
	} //GEN-LAST:event_btnActualSizeActionPerformed

	void btnFitWidthActionPerformed() { //GEN-FIRST:event_btnFitWidthActionPerformed
		//Add your handling code here:
		if(btnFitWidth.isSelected()) {
			btnActualSize.setSelected(false);
			btnFitPage.setSelected(false);
			setRealZoomRatio(((float)pnlInScroll.getVisibleRect().getWidth() - 20f) / jasperPrint.getPageWidth());
		}
	} //GEN-LAST:event_btnFitWidthActionPerformed

	void btnFitPageActionPerformed() { //GEN-FIRST:event_btnFitPageActionPerformed
		//Add your handling code here:
		if(btnFitPage.isSelected()) {
			btnActualSize.setSelected(false);
			btnFitWidth.setSelected(false);
			setRealZoomRatio(((float)pnlInScroll.getVisibleRect().getHeight() - 20f) / jasperPrint.getPageHeight());
		}
	} //GEN-LAST:event_btnFitPageActionPerformed

	void btnSaveActionPerformed() { //GEN-FIRST:event_btnSaveActionPerformed
		//Add your handling code here:
		JFileChooser fileChooser = new JFileChooser();
		FileFilter jrprintFileFilter = new FileFilter() {
			public boolean accept(File file) {
				if(file.isDirectory())
					return true;
				return file.getName().toLowerCase().endsWith(".jrprint");
			}

			public String getDescription() {
				return "JasperReports (*.jrprint)";
			}
		};
		fileChooser.addChoosableFileFilter(jrprintFileFilter);
		JRSaveContributor pdfSaveContrib = null;
		try {
			Class pdfSaveContribClass = JRClassLoader.loadClassForName("net.sf.jasperreports.view.save.JRPdfSaveContributor");
			pdfSaveContrib = (JRSaveContributor)pdfSaveContribClass.newInstance();
			fileChooser.addChoosableFileFilter(pdfSaveContrib);
		} catch(Exception e) {
		}
		JRSaveContributor rtfSaveContrib = null;
		try {
			Class rtfSaveContribClass = JRClassLoader.loadClassForName("net.sf.jasperreports.view.save.JRRtfSaveContributor");
			rtfSaveContrib = (JRSaveContributor)rtfSaveContribClass.newInstance();
			fileChooser.addChoosableFileFilter(rtfSaveContrib);
		} catch(Exception ex) {
		}
		JRSaveContributor htmlSaver = null;
		try {
			Class htmlSaverClass = JRClassLoader.loadClassForName("net.sf.jasperreports.view.save.JRHtmlSaveContributor");
			htmlSaver = (JRSaveContributor)htmlSaverClass.newInstance();
			fileChooser.addChoosableFileFilter(htmlSaver);
		} catch(Exception e) {
		}
		JRSaveContributor xlsSingleSheetSaver = null;
		try {
			Class xlsSingleSheetSaverClass = JRClassLoader.loadClassForName("net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor");
			xlsSingleSheetSaver = (JRSaveContributor)xlsSingleSheetSaverClass.newInstance();
			fileChooser.addChoosableFileFilter(xlsSingleSheetSaver);
		} catch(Exception e) {
		}
		JRSaveContributor xlsMultipleSheetsSaver = null;
		try {
			Class xlsMultipleSheetsSaverClass = JRClassLoader.loadClassForName("net.sf.jasperreports.view.save.JRMultipleSheetsXlsSaveContributor");
			xlsMultipleSheetsSaver = (JRSaveContributor)xlsMultipleSheetsSaverClass.newInstance();
			fileChooser.addChoosableFileFilter(xlsMultipleSheetsSaver);
		} catch(Exception e) {
		}
		JRSaveContributor csvSaver = null;
		try {
			Class csvSaverClass = JRClassLoader.loadClassForName("net.sf.jasperreports.view.save.JRCsvSaveContributor");
			csvSaver = (JRSaveContributor)csvSaverClass.newInstance();
			fileChooser.addChoosableFileFilter(csvSaver);
		} catch(Exception e) {
		}
		JRSaveContributor xmlSaver = null;
		try {
			Class xmlSaverClass = JRClassLoader.loadClassForName("net.sf.jasperreports.view.save.JRXmlSaveContributor");
			xmlSaver = (JRSaveContributor)xmlSaverClass.newInstance();
			fileChooser.addChoosableFileFilter(xmlSaver);
		} catch(Exception e) {
		}
		JRSaveContributor xmlEmbeddedImagesSaver = null;
		try {
			Class xmlEmbeddedImagesSaverClass = JRClassLoader.loadClassForName("net.sf.jasperreports.view.save.JREmbeddedImagesXmlSaveContributor");
			xmlEmbeddedImagesSaver = (JRSaveContributor)xmlEmbeddedImagesSaverClass.newInstance();
			fileChooser.addChoosableFileFilter(xmlEmbeddedImagesSaver);
		} catch(Exception e) {
		}
		fileChooser.setFileFilter(jrprintFileFilter);
		int retValue = fileChooser.showSaveDialog(this);
		if(retValue == JFileChooser.APPROVE_OPTION) {
			FileFilter fileFilter = fileChooser.getFileFilter();
			File file = fileChooser.getSelectedFile();
			String lowerCaseFileName = file.getName().toLowerCase();
			try {
				if(fileFilter == jrprintFileFilter) {
					if(!file.getName().endsWith(".jrprint")) {
						file = new File(file.getAbsolutePath() + ".jrprint");
					}
					JRSaver.saveObject(jasperPrint, file);
				} else if(fileFilter instanceof JRSaveContributor) {
					((JRSaveContributor)fileFilter).save(jasperPrint, file);
				} else {
					if(lowerCaseFileName.endsWith(".jrprint")) {
						JRSaver.saveObject(jasperPrint, file);
					} else if(lowerCaseFileName.endsWith(".pdf") && pdfSaveContrib != null) {
						pdfSaveContrib.save(jasperPrint, file);
					} else if((lowerCaseFileName.endsWith(".html") || lowerCaseFileName.endsWith(".htm")) && htmlSaver != null) {
						htmlSaver.save(jasperPrint, file);
					} else if(lowerCaseFileName.endsWith(".xls") && xlsSingleSheetSaver != null) {
						xlsSingleSheetSaver.save(jasperPrint, file);
					} else if(lowerCaseFileName.endsWith(".csv") && csvSaver != null) {
						csvSaver.save(jasperPrint, file);
					} else if(lowerCaseFileName.endsWith(".xml") || lowerCaseFileName.endsWith(".jrpxml") && xmlSaver != null) {
						xmlSaver.save(jasperPrint, file);
					} else {
						if(!file.getName().endsWith(".jrprint")) {
							file = new File(file.getAbsolutePath() + ".jrprint");
						}
						JRSaver.saveObject(jasperPrint, file);
					}
				}
			} catch(JRException e) {
				e.printStackTrace();
				String msgError = java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("error.saving");
				BossError.gestionarError(new PException(BossError.ERR_CONEXION, msgError, msgError, e, new String[] {}));
			}
		}
	} //GEN-LAST:event_btnSaveActionPerformed

	void pnlLinksMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLinksMouseDragged
		//Add your handling code here:
		Container container = pnlInScroll.getParent();
		if(container instanceof JViewport) {
			JViewport viewport = (JViewport)container;
			Point point = viewport.getViewPosition();
			int newX = point.x - (evt.getX() - downX);
			int newY = point.y - (evt.getY() - downY);
			int maxX = pnlInScroll.getWidth() - viewport.getWidth();
			int maxY = pnlInScroll.getHeight() - viewport.getHeight();
			if(newX < 0) {
				newX = 0;
			}
			if(newX > maxX) {
				newX = maxX;
			}
			if(newY < 0) {
				newY = 0;
			}
			if(newY > maxY) {
				newY = maxY;
			}
			viewport.setViewPosition(new Point(newX, newY));
		}
	} //GEN-LAST:event_pnlLinksMouseDragged

	void pnlLinksMouseReleased() {//GEN-FIRST:event_pnlLinksMouseReleased
		//Add your handling code here:
		pnlLinks.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	} //GEN-LAST:event_pnlLinksMouseReleased

	void pnlLinksMousePressed(java.awt.event.MouseEvent evt) { //GEN-FIRST:event_pnlLinksMousePressed
		//Add your handling code here:
		pnlLinks.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		downX = evt.getX();
		downY = evt.getY();
	} //GEN-LAST:event_pnlLinksMousePressed

	void btnPrintActionPerformed() //GEN-FIRST:event_btnPrintActionPerformed
	{ //GEN-HEADEREND:event_btnPrintActionPerformed
		//Add your handling code here:
		Thread thread = new Thread(new Runnable() {

			public void run() {
				try {
					JasperPrintManager.printReport(jasperPrint, true);
				} catch(Exception ex) {
					if(ex.getCause().getMessage().equals("Printer is not accepting job.")) {
						BossError.gestionarError(new PException(BossError.ERR_CONEXION, "No se puede imprimir",
								"La Impresora no responde o no est� accesible", ex, new String[] { "Verifique el estado de la impresora" }));
					} else {
						String msgError = java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("error.printing");
						BossError.gestionarError(new PException(BossError.ERR_CONEXION, msgError, msgError, ex, new String[] {}));
					}
				}
			}
		});
		thread.start();
	} //GEN-LAST:event_btnPrintActionPerformed

	void btnLastActionPerformed() //GEN-FIRST:event_btnLastActionPerformed
	{ //GEN-HEADEREND:event_btnLastActionPerformed
		//Add your handling code here:
		setPageIndex(jasperPrint.getPages().size() - 1);
		refreshPage();
	} //GEN-LAST:event_btnLastActionPerformed

	void btnNextActionPerformed() //GEN-FIRST:event_btnNextActionPerformed
	{ //GEN-HEADEREND:event_btnNextActionPerformed
		//Add your handling code here:
		setPageIndex(pageIndex + 1);
		refreshPage();
	} //GEN-LAST:event_btnNextActionPerformed

	void btnPreviousActionPerformed() //GEN-FIRST:event_btnPreviousActionPerformed
	{ //GEN-HEADEREND:event_btnPreviousActionPerformed
		//Add your handling code here:
		setPageIndex(pageIndex - 1);
		refreshPage();
	} //GEN-LAST:event_btnPreviousActionPerformed

	void btnFirstActionPerformed() //GEN-FIRST:event_btnFirstActionPerformed
	{ //GEN-HEADEREND:event_btnFirstActionPerformed
		//Add your handling code here:
		setPageIndex(0);
		refreshPage();
	} //GEN-LAST:event_btnFirstActionPerformed

	void btnReloadActionPerformed() //GEN-FIRST:event_btnReloadActionPerformed
	{ //GEN-HEADEREND:event_btnReloadActionPerformed
		//Add your handling code here:
		if(type == TYPE_FILE_NAME) {
			try {
				loadReport(reportFileName, isXML);
			} catch(JRException e) {
				e.printStackTrace();
				jasperPrint = null;
				setPageIndex(0);
				refreshPage();
				String msgError = java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("error.loading");
				BossError.gestionarError(new PException(BossError.ERR_CONEXION, msgError, msgError, e, new String[] {}));
			}
			zoom = 0; //force pageRefresh()
			realZoom = 0f;
			setZoomRatio(1);
		}
	} //GEN-LAST:event_btnReloadActionPerformed

	void btnZoomInActionPerformed() //GEN-FIRST:event_btnZoomInActionPerformed
	{ //GEN-HEADEREND:event_btnZoomInActionPerformed
		//Add your handling code here:
		btnActualSize.setSelected(false);
		btnFitPage.setSelected(false);
		btnFitWidth.setSelected(false);
		int newZoomInt = (int)(100 * getZoomRatio());
		int index = Arrays.binarySearch(zooms, newZoomInt);
		if(index < 0) {
			setZoomRatio(zooms[-index - 1] / 100f);
		} else if(index < cmbZoom.getModel().getSize() - 1) {
			setZoomRatio(zooms[index + 1] / 100f);
		}
	} //GEN-LAST:event_btnZoomInActionPerformed

	void btnZoomOutActionPerformed() //GEN-FIRST:event_btnZoomOutActionPerformed
	{ //GEN-HEADEREND:event_btnZoomOutActionPerformed
		//Add your handling code here:
		btnActualSize.setSelected(false);
		btnFitPage.setSelected(false);
		btnFitWidth.setSelected(false);
		int newZoomInt = (int)(100 * getZoomRatio());
		int index = Arrays.binarySearch(zooms, newZoomInt);
		if(index > 0) {
			setZoomRatio(zooms[index - 1] / 100f);
		} else if(index < -1) {
			setZoomRatio(zooms[-index - 2] / 100f);
		}
	} //GEN-LAST:event_btnZoomOutActionPerformed

	void cmbZoomActionPerformed() //GEN-FIRST:event_cmbZoomActionPerformed
	{ //GEN-HEADEREND:event_cmbZoomActionPerformed
		//Add your handling code here:
		float newZoom = getZoomRatio();
		if(newZoom < MIN_ZOOM) {
			newZoom = MIN_ZOOM;
		}
		if(newZoom > MAX_ZOOM) {
			newZoom = MAX_ZOOM;
		}
		setZoomRatio(newZoom);
	} //GEN-LAST:event_cmbZoomActionPerformed

	void hyperlinkClicked(MouseEvent evt) {
		JPanel link = (JPanel)evt.getSource();
		JRPrintHyperlink element = (JRPrintHyperlink)linksMap.get(link);
		try {
			JRHyperlinkListener listener = null;
			for(int i = 0; i < hyperlinkListeners.size(); i++) {
				listener = (JRHyperlinkListener)hyperlinkListeners.get(i);
				listener.gotoHyperlink(element);
			}
		} catch(JRException e) {
			e.printStackTrace();
			String msgError = java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("error.hyperlink");
			BossError.gestionarError(new PException(BossError.ERR_CONEXION, msgError, msgError, e, new String[] {}));
		}
	}

	private void setPageIndex(int index) {
		pageIndex = index;
		if(jasperPrint != null && jasperPrint.getPages() != null && jasperPrint.getPages().size() > 0) {
			btnFirst.setEnabled((pageIndex > 0));
			btnPrevious.setEnabled((pageIndex > 0));
			btnNext.setEnabled((pageIndex < jasperPrint.getPages().size() - 1));
			btnLast.setEnabled((pageIndex < jasperPrint.getPages().size() - 1));
			txtGoTo.setEnabled(btnFirst.isEnabled() || btnLast.isEnabled());
			txtGoTo.setText("" + (pageIndex + 1));
			lblStatus.setText(MessageFormat.format(java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("page"), new Object[] {
					new Integer(pageIndex + 1), new Integer(jasperPrint.getPages().size()) }));
		} else {
			btnFirst.setEnabled(false);
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(false);
			btnLast.setEnabled(false);
			txtGoTo.setEnabled(false);
			txtGoTo.setText("");
			lblStatus.setText("");
		}
	}

	protected void loadReport(String fileName, boolean isXmlReport) throws JRException {
		if(isXmlReport) {
			jasperPrint = JRPrintXmlLoader.load(fileName);
		} else {
			jasperPrint = (JasperPrint)JRLoader.loadObject(fileName);
		}
		type = TYPE_FILE_NAME;
		this.isXML = isXmlReport;
		reportFileName = fileName;
		btnReload.setEnabled(true);
		setPageIndex(0);
	}

	protected void loadReport(InputStream is, boolean isXmlReport) throws JRException {
		if(isXmlReport) {
			jasperPrint = JRPrintXmlLoader.load(is);
		} else {
			jasperPrint = (JasperPrint)JRLoader.loadObject(is);
		}
		type = TYPE_INPUT_STREAM;
		this.isXML = isXmlReport;
		btnReload.setEnabled(false);
		setPageIndex(0);
	}

	protected void loadReport(JasperPrint jrPrint) {
		jasperPrint = jrPrint;
		type = TYPE_JASPER_PRINT;
		isXML = false;
		btnReload.setEnabled(false);
		setPageIndex(0);
	}

	@SuppressWarnings("unchecked")
	protected void refreshPage() {
		if(jasperPrint == null || jasperPrint.getPages() == null || jasperPrint.getPages().size() == 0) {
			pnlPage.setVisible(false);
			btnSave.setEnabled(false);
			btnPrint.setEnabled(false);
			btnActualSize.setEnabled(false);
			btnFitPage.setEnabled(false);
			btnFitWidth.setEnabled(false);
			btnZoomIn.setEnabled(false);
			btnZoomOut.setEnabled(false);
			cmbZoom.setEnabled(false);
			if(jasperPrint != null) {
				JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("no.pages"));
			}
			return;
		}
		pnlPage.setVisible(true);
		btnSave.setEnabled(true);
		btnPrint.setEnabled(true);
		btnActualSize.setEnabled(true);
		btnFitPage.setEnabled(true);
		btnFitWidth.setEnabled(true);
		btnZoomIn.setEnabled(zoom < MAX_ZOOM);
		btnZoomOut.setEnabled(zoom > MIN_ZOOM);
		cmbZoom.setEnabled(true);
		Image image = null;
		ImageIcon imageIcon = null;
		Dimension dim = new Dimension((int)(jasperPrint.getPageWidth() * realZoom) + 8, //2
				(int)(jasperPrint.getPageHeight() * realZoom) + 8);
		pnlPage.setMaximumSize(dim);
		pnlPage.setMinimumSize(dim);
		pnlPage.setPreferredSize(dim);
		try {
			image = JasperPrintManager.printPageToImage(jasperPrint, pageIndex, realZoom);
			imageIcon = new ImageIcon(image);
		} catch(Exception e) {
			e.printStackTrace();
			String msgError = java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("error.displaying");
			BossError.gestionarError(new PException(BossError.ERR_CONEXION, msgError, msgError, e, new String[] {}));
		}
		pnlLinks.removeAll();
		linksMap = new HashMap();
		java.util.List pages = jasperPrint.getPages();
		JRPrintPage page = (JRPrintPage)pages.get(pageIndex);
		Collection elements = page.getElements();
		if(elements != null && elements.size() > 0) {
			String toolTip = null;
			JPanel link = null;
			JRPrintElement element = null;
			JRPrintHyperlink hyperlink = null;
			for(Iterator it = elements.iterator(); it.hasNext();) {
				element = (JRPrintElement)it.next();
				if(element instanceof JRPrintHyperlink && ((JRPrintHyperlink)element).getHyperlinkType() != JRHyperlink.HYPERLINK_TYPE_NONE) {
					hyperlink = (JRPrintHyperlink)element;
					link = new JPanel();
					link.setCursor(new Cursor(Cursor.HAND_CURSOR));
					link.setLocation((int)(element.getX() * realZoom), (int)(element.getY() * realZoom));
					link.setSize((int)(element.getWidth() * realZoom), (int)(element.getHeight() * realZoom));
					link.setOpaque(false);
					toolTip = null;
					switch(hyperlink.getHyperlinkType()) {
						case JRHyperlink.HYPERLINK_TYPE_REFERENCE: {
							toolTip = hyperlink.getHyperlinkReference();
							break;
						}
						case JRHyperlink.HYPERLINK_TYPE_LOCAL_ANCHOR: {
							if(hyperlink.getHyperlinkAnchor() != null) {
								toolTip = "#" + hyperlink.getHyperlinkAnchor();
							}
							break;
						}
						case JRHyperlink.HYPERLINK_TYPE_LOCAL_PAGE: {
							if(hyperlink.getHyperlinkPage() != null) {
								toolTip = "#page " + hyperlink.getHyperlinkPage();
							}
							break;
						}
						case JRHyperlink.HYPERLINK_TYPE_REMOTE_ANCHOR: {
							toolTip = "";
							if(hyperlink.getHyperlinkReference() != null) {
								toolTip = toolTip + hyperlink.getHyperlinkReference();
							}
							if(hyperlink.getHyperlinkAnchor() != null) {
								toolTip = toolTip + "#" + hyperlink.getHyperlinkAnchor();
							}
							break;
						}
						case JRHyperlink.HYPERLINK_TYPE_REMOTE_PAGE: {
							toolTip = "";
							if(hyperlink.getHyperlinkReference() != null) {
								toolTip = toolTip + hyperlink.getHyperlinkReference();
							}
							if(hyperlink.getHyperlinkPage() != null) {
								toolTip = toolTip + "#page " + hyperlink.getHyperlinkPage();
							}
							break;
						}
						default: {
							break;
						}
					}
					link.setToolTipText(toolTip);
					link.addMouseListener(mouseListener);
					pnlLinks.add(link);
					linksMap.put(link, element);
				}
			}
		}
		lblPage.setIcon(imageIcon);
	}

	private void emptyContainer(Container container) {
		Component[] components = container.getComponents();
		if(components != null) {
			for(int i = 0; i < components.length; i++) {
				if(components[i] instanceof Container) {
					emptyContainer((Container)components[i]);
				}
			}
		}
		components = null;
		container.removeAll();
		container = null;
	}

	private float getZoomRatio() {
		float newZoom = zoom;
		try {
			newZoom = zoomDecimalFormat.parse(String.valueOf(cmbZoom.getEditor().getItem())).floatValue() / 100f;
		} catch(ParseException e) {
		}
		return newZoom;
	}

	private void setZoomRatio(float newZoom) {
		if(newZoom > 0) {
			cmbZoom.getEditor().setItem(zoomDecimalFormat.format(newZoom * 100) + "%");
			if(zoom != newZoom) {
				zoom = newZoom;
				realZoom = zoom * screenResolution / REPORT_RESOLUTION;
				refreshPage();
			}
		}
	}

	private void setRealZoomRatio(float newZoom) {
		if(newZoom > 0 && realZoom != newZoom) {
			zoom = newZoom * REPORT_RESOLUTION / screenResolution;
			realZoom = newZoom;
			cmbZoom.getEditor().setItem(zoomDecimalFormat.format(zoom * 100) + "%");
			refreshPage();
		}
	}

	//Variables declaration - do not modify //GEN-BEGIN:variables
	protected javax.swing.JToggleButton btnActualSize;
	protected javax.swing.JButton btnFirst;
	protected javax.swing.JToggleButton btnFitPage;
	protected javax.swing.JToggleButton btnFitWidth;
	protected javax.swing.JButton btnLast;
	protected javax.swing.JButton btnNext;
	protected javax.swing.JButton btnPrevious;
	protected javax.swing.JButton btnPrint;
	protected javax.swing.JButton btnReload;
	protected javax.swing.JButton btnSave;
	protected javax.swing.JButton btnZoomIn;
	protected javax.swing.JButton btnZoomOut;
	protected javax.swing.JComboBox cmbZoom;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JLabel lblPage;
	protected javax.swing.JLabel lblStatus;
	private javax.swing.JPanel pnlInScroll;
	private javax.swing.JPanel pnlLinks;
	private javax.swing.JPanel pnlMain;
	private javax.swing.JPanel pnlPage;
	protected javax.swing.JPanel pnlSep01;
	protected javax.swing.JPanel pnlSep02;
	protected javax.swing.JPanel pnlSep03;
	protected javax.swing.JPanel pnlStatus;
	private javax.swing.JScrollPane scrollPane;
	protected javax.swing.JPanel tlbToolBar;
	protected javax.swing.JTextField txtGoTo;
	//End of variables declaration//GEN-END:variables

}