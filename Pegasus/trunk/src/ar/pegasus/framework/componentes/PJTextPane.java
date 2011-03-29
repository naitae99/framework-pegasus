package ar.pegasus.framework.componentes;

import java.awt.Cursor;
import java.awt.Insets;
import java.io.CharArrayWriter;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.StyleSheet;

import ar.pegasus.framework.componentes.PJTextArea.ExtendedFixedSizeFilter;
import ar.pegasus.framework.componentes.textComponents.ExtendedHTMLDocument;
import ar.pegasus.framework.componentes.textComponents.ExtendedHTMLEditorKit;
import ar.pegasus.framework.util.StringUtil;

public class PJTextPane extends JTextPane
{
	private static final long		serialVersionUID	= -6957724641814882606L;
	private ExtendedHTMLEditorKit	htmlKit;
	private ExtendedHTMLDocument	htmlDoc;
	private StyleSheet				styleSheet;

	
	public PJTextPane(int maxLong)
	{
		htmlKit = new ExtendedHTMLEditorKit();
		htmlDoc = (ExtendedHTMLDocument) (htmlKit.createDefaultDocument());
		htmlKit.setDefaultCursor(new Cursor(Cursor.TEXT_CURSOR));
		setCursor(new Cursor(Cursor.TEXT_CURSOR));
		styleSheet = htmlDoc.getStyleSheet();
		setDocument(htmlDoc);
		setEditorKit(htmlKit);
		htmlDoc.setDocumentFilter(new ExtendedFixedSizeFilter(maxLong));
		setMargin(new Insets(4, 4, 4, 4));
	}
	
	public ExtendedHTMLDocument getHTMLDocumentWithStyleSheet()
	{
		return new ExtendedHTMLDocument(styleSheet);
	}
	
	public String getFixedAndFormattedText() throws BadLocationException, IOException
	{
		CharArrayWriter fw = new CharArrayWriter();
		getHtmlKit().write(fw, this.getDocument(), 0, this.getDocument().getLength());
		fw.flush();
		String textoResultado = fw.toString();
		
		textoResultado = textoResultado.replaceAll("<p>", "");
		textoResultado = textoResultado.replaceAll("</p>", "");
		textoResultado = textoResultado.replaceAll("<p style=\"margin-top: 0\">", "");
		textoResultado = textoResultado.replaceAll("<head>", "");
		textoResultado = textoResultado.replaceAll("</head>", "");
		textoResultado = textoResultado.replaceAll("<html>", "");
		textoResultado = textoResultado.replaceAll("</html>", "");
		textoResultado = textoResultado.replaceAll("<body>", "");
		textoResultado = textoResultado.replaceAll("</body>", "");
		
		textoResultado = textoResultado.replaceAll("</b>", "</strong>");
		textoResultado = textoResultado.replaceAll("<b>", "<strong>");
		
		textoResultado = escaparAcentos(textoResultado);
		textoResultado = textoResultado.trim();
		return textoResultado;
	}
	
	public String getFixedAndFormattedText2() throws BadLocationException, IOException
	{
		CharArrayWriter fw = new CharArrayWriter();
		getHtmlKit().write(fw, this.getDocument(), 0, this.getDocument().getLength());
		fw.flush();
		String textoResultado = fw.toString();
		
		
		textoResultado = textoResultado.replaceAll("\n", "");
		textoResultado = textoResultado.replaceAll("\r\n", "");
		textoResultado = textoResultado.replaceAll("<p[^>]*>([^<]*)</p>", "\n$1");
		
		textoResultado = textoResultado.replaceAll("<head>", "");
		textoResultado = textoResultado.replaceAll("</head>", "");
		textoResultado = textoResultado.replaceAll("<html>", "");
		textoResultado = textoResultado.replaceAll("</html>", "");
		textoResultado = textoResultado.replaceAll("<body>", "");
		textoResultado = textoResultado.replaceAll("</body>", "");
		textoResultado = textoResultado.replaceAll("      ", "");
		textoResultado = textoResultado.replaceAll("    ", "");
		textoResultado = escaparAcentos(textoResultado);
		textoResultado = textoResultado.trim();
		return textoResultado;
	}
	
	public String getFixedText() throws BadLocationException, IOException
	{
		CharArrayWriter fw = new CharArrayWriter();
		getHtmlKit().write(fw, this.getDocument(), 0, this.getDocument().getLength());
		fw.flush();
		String textoResultado = fw.toString();
		
		textoResultado = textoResultado.replaceAll("<p>", "");
		textoResultado = textoResultado.replaceAll("</p>", "");
		textoResultado = textoResultado.replaceAll("<p style=\"margin-top: 0\">", "");
		textoResultado = textoResultado.replaceAll("<head>", "");
		textoResultado = textoResultado.replaceAll("</head>", "");
		textoResultado = textoResultado.replaceAll("<html>", "");
		textoResultado = textoResultado.replaceAll("</html>", "");
		textoResultado = textoResultado.replaceAll("<body>", "");
		textoResultado = textoResultado.replaceAll("</body>", "");
		textoResultado = textoResultado.replaceAll("<s*a.*?>", "");
		textoResultado = textoResultado.replaceAll("<\\s*/\\s*a\\s*>", "");
		textoResultado = textoResultado.replaceAll("<s*font.*?>", "");
		textoResultado = textoResultado.replaceAll("<\\s*/\\s*font\\s*>", "");
		textoResultado = textoResultado.replaceAll("<s*span.*?>", "");
		textoResultado = textoResultado.replaceAll("<\\s*/\\s*span\\s*>", "");
		textoResultado = textoResultado.replaceAll("<i>", "");
		textoResultado = textoResultado.replaceAll("</i>", "");
		textoResultado = textoResultado.replaceAll("<b>", "");
		textoResultado = textoResultado.replaceAll("</b>", "");
		textoResultado = textoResultado.replaceAll("<u>", "");
		textoResultado = textoResultado.replaceAll("</u>", "");
		textoResultado = escaparAcentos(textoResultado);
		textoResultado = textoResultado.trim();
		return textoResultado;
	}
	private String escaparAcentos(String textoResultado)
	{
		textoResultado = textoResultado.replaceAll("&#225;", "\u00E1");
		textoResultado = textoResultado.replaceAll("&#233;", "\u00E9");
		textoResultado = textoResultado.replaceAll("&#237;", "\u00ED");
		textoResultado = textoResultado.replaceAll("&#243;", "\u00F3");
		textoResultado = textoResultado.replaceAll("&#250;", "\u00FA");
		textoResultado = textoResultado.replaceAll("&#241;", "\u00F1");
		
		textoResultado = textoResultado.replaceAll("&#193;", "\u00C1");
		textoResultado = textoResultado.replaceAll("&#201;", "\u00C9");
		textoResultado = textoResultado.replaceAll("&#205;", "\u00CD");
		textoResultado = textoResultado.replaceAll("&#211;", "\u00D3");
		textoResultado = textoResultado.replaceAll("&#218;", "\u00DA");
		textoResultado = textoResultado.replaceAll("&#209;", "\u00D1");
		
		return textoResultado;
	}

	public String getHTMLText()throws BadLocationException,IOException{
		CharArrayWriter fw = new CharArrayWriter();
		getHtmlKit().write(fw, this.getDocument(), 0, this.getDocument().getLength());
		fw.flush();
		String textoResultado = fw.toString();
		return textoResultado;
	}
	public String getUnFormatedText() throws BadLocationException, IOException
	{
		String textFixed = getFixedText();
		textFixed = textFixed.replaceAll("\\r\\n    \\r\\n    \\r\\n      ", "");
		String [] tokens = StringUtil.getTokens(textFixed, " ");
		textFixed = StringUtil.getCadena(tokens, " ");
		return textFixed;
	}
	public String getFixedTextDEH() throws BadLocationException, IOException
	{
		CharArrayWriter fw = new CharArrayWriter();
		getHtmlKit().write(fw, this.getDocument(), 0, this.getDocument().getLength());
		fw.flush();
		String textoResultado = fw.toString();
		
		textoResultado = textoResultado.replaceAll("<p>", "");
		textoResultado = textoResultado.replaceAll("</p>", "");
		textoResultado = textoResultado.replaceAll("<p style=\"margin-top: 0\">", "");
		textoResultado = textoResultado.replaceAll("<head>", "");
		textoResultado = textoResultado.replaceAll("</head>", "");
		textoResultado = textoResultado.replaceAll("<html>", "");
		textoResultado = textoResultado.replaceAll("</html>", "");
		textoResultado = textoResultado.replaceAll("<body>", "");
		textoResultado = textoResultado.replaceAll("</body>", "");
		textoResultado = textoResultado.replaceAll("<s*a.*?>", "");
		textoResultado = textoResultado.replaceAll("<\\s*/\\s*a\\s*>", "");
		textoResultado = textoResultado.replaceAll("<s*font.*?>", "");
		textoResultado = textoResultado.replaceAll("<\\s*/\\s*font\\s*>", "");
		textoResultado = textoResultado.replaceAll("<s*span.*?>", "");
		textoResultado = textoResultado.replaceAll("<\\s*/\\s*span\\s*>", "");
		textoResultado = textoResultado.replaceAll("<i>", "");
		textoResultado = textoResultado.replaceAll("</i>", "");
		textoResultado = textoResultado.replaceAll("<b>", "");
		textoResultado = textoResultado.replaceAll("</b>", "");
		textoResultado = textoResultado.replaceAll("<u>", "");
		textoResultado = textoResultado.replaceAll("</u>", "");
		textoResultado = textoResultado.replaceAll("\\r\\n    \\r\\n    \\r\\n      ", "");
		textoResultado= textoResultado.replace("</strong>", "");
		textoResultado= textoResultado.replace("<strong>", "");
		textoResultado= textoResultado.replace("&#161;", "�");
		textoResultado = textoResultado.replaceAll("&#225;", "\u00E1");
		textoResultado = textoResultado.replaceAll("&#233;", "\u00E9");
		textoResultado = textoResultado.replaceAll("&#237;", "\u00ED");
		textoResultado = textoResultado.replaceAll("&#243;", "\u00F3");
		textoResultado = textoResultado.replaceAll("&#250;", "\u00FA");
		textoResultado = textoResultado.replaceAll("&#241;", "\u00F1");
		
		textoResultado = textoResultado.replaceAll("&#193;", "\u00C1");
		textoResultado = textoResultado.replaceAll("&#201;", "\u00C9");
		textoResultado = textoResultado.replaceAll("&#205;", "\u00CD");
		textoResultado = textoResultado.replaceAll("&#211;", "\u00D3");
		textoResultado = textoResultado.replaceAll("&#218;", "\u00DA");
		textoResultado = textoResultado.replaceAll("&#209;", "\u00D1");
		textoResultado = escaparAcentos(textoResultado);
		textoResultado = textoResultado.trim();
		return textoResultado;
	}
	public ExtendedHTMLEditorKit getHtmlKit()
	{
		return htmlKit;
	}
	
	public void setHtmlKit(ExtendedHTMLEditorKit htmlKit)
	{
		this.htmlKit = htmlKit;
	}
	
	public ExtendedHTMLDocument getHtmlDoc()
	{
		return htmlDoc;
	}
	
	public void setHtmlDoc(ExtendedHTMLDocument htmlDoc)
	{
		this.htmlDoc = htmlDoc;
	}
	
	public StyleSheet getStyleSheet()
	{
		return styleSheet;
	}
	
	public void setStyleSheet(StyleSheet styleSheet)
	{
		this.styleSheet = styleSheet;
	}
}
