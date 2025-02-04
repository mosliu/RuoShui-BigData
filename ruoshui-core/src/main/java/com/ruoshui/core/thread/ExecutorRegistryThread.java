package com.ruoshui.core.thread;

import com.ruoshui.core.biz.AdminBiz;
import com.ruoshui.core.biz.model.RegistryParam;
import com.ruoshui.core.biz.model.ReturnT;
import com.ruoshui.core.enums.RegistryConfig;
import com.ruoshui.core.executor.JobExecutor;
import com.ruoshui.core.util.OSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by xuxueli on 17/3/2.
 */
public class ExecutorRegistryThread {
    private static Logger logger = LoggerFactory.getLogger(ExecutorRegistryThread.class);

    private static ExecutorRegistryThread instance = new ExecutorRegistryThread();
    public static ExecutorRegistryThread getInstance(){
        return instance;
    }

    private Thread registryThread;
    private volatile boolean toStop = false;
    public void start(final String appName, final String address){

        // valid
        if (appName==null || appName.trim().length()==0) {
            logger.warn(">>>>>>>>>>> web, executor registry config fail, appName is null.");
            return;
        }
        if (JobExecutor.getAdminBizList() == null) {
            logger.warn(">>>>>>>>>>> web, executor registry config fail, adminAddresses is null.");
            return;
        }

        registryThread = new Thread(() -> {

            // registry
            while (!toStop) {
                try {
                    RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), appName, address, OSUtils.cpuUsage(),OSUtils.memoryUsage(),OSUtils.loadAverage());
                    for (AdminBiz adminBiz: JobExecutor.getAdminBizList()) {
                        try {
                            ReturnT<String> registryResult = adminBiz.registry(registryParam);
                            if (registryResult!=null && ReturnT.SUCCESS_CODE == registryResult.getCode()) {
                                registryResult = ReturnT.SUCCESS;
                                logger.debug(">>>>>>>>>>> web registry success, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                                break;
                            } else {
                                logger.info(">>>>>>>>>>> web registry fail, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                            }
                        } catch (Exception e) {
                            logger.info(">>>>>>>>>>> web registry error, registryParam:{}", registryParam, e);
                        }

                    }
                } catch (Exception e) {
                    if (!toStop) {
                        logger.error(e.getMessage(), e);
                    }

                }

                try {
                    if (!toStop) {
                        TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
                    }
                } catch (InterruptedException e) {
                    if (!toStop) {
                        logger.warn(">>>>>>>>>>> web, executor registry thread interrupted, error msg:{}", e.getMessage());
                    }
                }
            }

            // registry remove
            try {
                RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), appName, address);
                for (AdminBiz adminBiz: JobExecutor.getAdminBizList()) {
                    try {
                        ReturnT<String> registryResult = adminBiz.registryRemove(registryParam);
                        if (registryResult!=null && ReturnT.SUCCESS_CODE == registryResult.getCode()) {
                            registryResult = ReturnT.SUCCESS;
                            logger.info(">>>>>>>>>>> web registry-remove success, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                            break;
                        } else {
                            logger.info(">>>>>>>>>>> web registry-remove fail, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                        }
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.info(">>>>>>>>>>> web registry-remove error, registryParam:{}", registryParam, e);
                        }

                    }

                }
            } catch (Exception e) {
                if (!toStop) {
                    logger.error(e.getMessage(), e);
                }
            }
            logger.info(">>>>>>>>>>> web, executor registry thread destory.");

        });
        registryThread.setDaemon(true);
        registryThread.setName("web, executor ExecutorRegistryThread");
        registryThread.start();
    }

    public void toStop() {
        toStop = true;
        // interrupt and wait
        registryThread.interrupt();
        try {
            registryThread.join();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
