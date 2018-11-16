package com.ebase.report.core;

import java.util.MissingResourceException;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * <p>
 * 错误国际化/展示信息国际化
 * </p>
 *
 * @project core
 * @class I18NResource
 */
public class I18nResource {
    // 消息类 I18N文件
    private static ReloadableResourceBundleMessageSource i18nMessage;
    // 异常类 I18N文件
    private static ReloadableResourceBundleMessageSource i18nException;

    static {
        i18nMessage = new ReloadableResourceBundleMessageSource();
        i18nMessage.setBasenames(new String[] { Constants.I18N_MESSAGE, "classpath:" + Constants.I18N_MESSAGE });

        i18nException = new ReloadableResourceBundleMessageSource();
        i18nException.setBasenames(new String[] { Constants.I18N_EXCEPTION, "classpath:" + Constants.I18N_EXCEPTION });
    }

    // 消息类I18N资源码 国际化
    public static String getMessage(String messageKey, Object[] messageArgs) {
        String message = null;
        try {
            message = i18nMessage.getMessage(messageKey, messageArgs, LocaleContextHolder.getLocale());
        } catch (MissingResourceException mse) {
            message = "i18nMessage is: " + messageKey + ", can't get the I18n properties";
        } catch (Throwable t) {
            message = "i18nMessage is: " + messageKey + ", can't get the I18n properties";
        }

        return message;
    }

    // 异常类I18N资源码 国际化
    public static String getException(String code, Object[] messageArgs) {
        String message = null;
        try {
            // todo code is not empty and i18n key maybe empty
            // get i18n key from exception props
            message = i18nException.getMessage(code, messageArgs, LocaleContextHolder.getLocale());
        } catch (MissingResourceException mse) {
            message = "i18nException is: " + code + ", can't get the I18n properties";
        } catch (Throwable t) {
            message = "i18nException is: " + code + ", can't get the I18n properties";
        }

        return message;
    }

    public static String getMessage(String messageKey) {
        return getMessage(messageKey, null);
    }

}
