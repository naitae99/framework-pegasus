package ar.pegasus.framework.mail;

/**
 * @author oarias
 */
public interface MailObserver {

	public void update(MailSender.MailInfo mailInfo);

}