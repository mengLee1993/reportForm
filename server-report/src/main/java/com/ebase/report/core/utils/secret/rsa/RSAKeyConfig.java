package com.ebase.report.core.utils.secret.rsa;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * <p>
 * RSA 公钥和私钥的获取
 * </p>
 *
 * @project core
 * @class RSAKeyConfig
 */
public class RSAKeyConfig {

	private final static Logger log = LoggerFactory.getLogger(RSAKeyConfig.class);

	public static final String X509 = "X.509";

	public static final String KEY_STORE = "JKS";

	private RSAPublicKey rsaPublicKey = null;

	private RSAPrivateKey rsaPrivateKey = null;

	private String certificatePath;

	private String keyStorePath;

	private String alias;

	private String password;

	/**
	 * 获取公钥
	 * 
	 * @return
	 */
	public RSAPublicKey getRsaPublicKey() {
		return rsaPublicKey;
	}

	/**
	 * 获取私钥
	 * 
	 * @return
	 */
	public RSAPrivateKey getRsaPrivateKey() {
		return rsaPrivateKey;
	}

	public void init() {
		Assert.notNull(certificatePath, "");
		Assert.notNull(keyStorePath, "");
		Assert.notNull(alias, "");
		Assert.notNull(password, "");

		if (rsaPublicKey == null) {
			InputStream inputStream = null;
			try {
				log.info("certificatePath:" + certificatePath);
				CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
				inputStream = RSAKeyConfig.class.getClassLoader().getResourceAsStream(certificatePath);
				Certificate certificate = certificateFactory.generateCertificate(inputStream);
				rsaPublicKey = (RSAPublicKey) certificate.getPublicKey();
			} catch (RuntimeException rex) {
				throw rex;
			} catch (Exception ex) {
				throw new RuntimeException("rsa私钥不存在!", ex);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}

		if (rsaPrivateKey == null) {
			InputStream inputStream = null;
			try {
				log.info("keyStorePath:" + keyStorePath + "   alias:" + alias);
				inputStream = RSAKeyConfig.class.getClassLoader().getResourceAsStream(keyStorePath);
				KeyStore keyStore = KeyStore.getInstance(KEY_STORE);
				keyStore.load(inputStream, password.toCharArray());
				rsaPrivateKey = (RSAPrivateKey) keyStore.getKey(alias, password.toCharArray());
			} catch (RuntimeException rex) {
				throw rex;
			} catch (Exception ex) {
				throw new RuntimeException("rsa秘钥不存在!", ex);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
