package com.ebase.report.core.db.handler;

import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.exception.DbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class AccessorFactory {
    //
    private static final Logger logger = LoggerFactory.getLogger(AccessorFactory.class);

    //
    private static volatile AccessorFactory instance;

    //
    private static Map<String, Object> accessorMap = new HashMap<String, Object>();

    //
    public static AccessorFactory get() {
        if (null == instance) {
            synchronized (AccessorFactory.class) {
                if (instance == null) {
                    instance = new AccessorFactory();
                }
            }
        }
        return instance;
    }

    //
    public synchronized <T> T factoryAccessor(Class<T> clazz, DataBaseType dataBaseType) throws DbException {
        //
        String className = clazz.getName() + dataBaseType.getSuffix();

        //
        if (!accessorMap.containsKey(className)) {
            try {
                T accessor = (T) (Class.forName(className).newInstance());

                accessorMap.put(className, accessor);
            } catch (InstantiationException e) {
                logger.error("Can't initialize accessor: " + clazz);

                throw new IllegalArgumentException("Class not supported: " + clazz + dataBaseType.getSuffix());
            } catch (IllegalAccessException e) {
                logger.error("Can't initialize accessor: " + clazz);

                throw new IllegalArgumentException("Class not supported: " + clazz + dataBaseType.getSuffix());
            } catch (ClassNotFoundException e) {
                logger.error("Can't initialize accessor: " + clazz);

                throw new IllegalArgumentException("Class not found: " + clazz + dataBaseType.getSuffix());
            }
        }

        //
        return (T) accessorMap.get(className);
    }
}
