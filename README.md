# RuoShui-BigData

#### 介绍
本系统以若依为基础开发框架
集成Datax-web、flink-streaming-platform-web、datax-cloud这三大开源服务
实现数据同步集成、数据开发、元数据管理、数据服务一体化
方便小型企业及个人开发者使用开发
### 内置功能
* 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
* 部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
* 岗位管理：配置系统用户所属担任职务。
* 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
* 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
* 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
* 参数管理：对系统动态配置常用参数。
* 通知公告：系统通知公告信息发布维护。
* 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
* 登录日志：系统登录日志记录查询包含登录异常。
* 在线用户：当前系统中活跃用户状态监控。
* 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
* 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
* 系统接口：根据业务代码自动生成相关的api接口文档。
* 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
* 缓存监控：对系统的缓存查询，删除、清空等操作。
* 在线构建器：拖动表单元素生成相应的HTML代码。
* 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。
* 数据集成：包含调度模板、任务构建、任务批量构建、实例管理、执行日志、资源监控这几个子功能;支持各数据源的相互同步且实现高可用（集成Datax-Web 所有功能 开源地址：https://gitee.com/WeiYe-Jing/datax-web）
* 数据开发：包含任务列表、历史版本、运行日志、告警设置、告警日志、系统设置、Jar包管理这几个子功能;支持flinkSql、flinkCDC、以及相关flink Jar包的任务上传执行并监控任务状态等等（集成flink-streaming-platform-web所有功能  开源地址：https://gitee.com/zhuhuipei/flink-streaming-platform-web）
* 数据资产：包含数据源管理、元数据管理、数据血缘（待和同步任务结合）、Sql工作台、元数据变更记录这几个子功能;管理各数据源的元数据及数据类型等等（集成datax-cloud部分功能 开源地址：https://gitee.com/yuwei1203/datax-cloud）
* 数据标准：包含标准字典、对照表单、字典对照、对照统计这几个子功能;支持按照字典去自动对照元数据的字段标准来统计字段的标准程度
* 数据质量：包含规则配置、问题统计、质量报告这几个子功能;支持通过配置质量规则来自动检测数据的质量并生成相应的质量报告
* 数据API：包含数据服务、数据脱敏、API调用日志;支持SQL取数、整表取数、api限流、api黑白名单、字段脱敏等功能
（后续会继续优化现有功能，开发商业业务功能）
#### 软件架构
![img.png](img/img.png)
#### 系统截图

1. 系统整体功能
![img_1.png](img/img_1.png)
2. 系统管理
![img_2.png](img/img_2.png)
3. 系统监控
![img_3.png](img/img_3.png)
4. 基础建设
![img_4.png](img/img_4.png)
![img_5.png](img/img_5.png)
![img_6.png](img/img_6.png)
![img_7.png](img/img_7.png)
5. 数据集成
![img_8.png](img/img_8.png)
6. 数据开发
![img_9.png](img/img_9.png)
![img_10.png](img/img_10.png)
7. 数据资产
![img_11.png](img/img_11.png)
![img_12.png](img/img_12.png)
![img_13.png](img/img_13.png)
8. 数据标准
![img_14.png](img/img_14.png)
![img_15.png](img/img_15.png)
9. 数据质量
![img_16.png](img/img_16.png)
![img_17.png](img/img_17.png)
10. 数据API
![img_18.png](img/img_18.png)
#### 使用说明
1. 拉取代码
2. 修改ruoshui-admin---->src--->main--->resources下的application.yml配置文件
![img.png](img/img50.png)
![img_1.png](img/img51.png)
![img.png](img/img52.png)
3. 修改ruoshui-admin---->src--->main--->resources下的application-druid.yml配置文件
![img.png](img/img53.png)
4. 修改ruoshui-datax-executor---->src--->main--->resources下的application.yml配置文件
![img.png](img/img54.png)
5. 将sql目录下的sql文件导入到自己数据库
6. 下载pluginLibs数据源jar包放到RuoShui-BigData主目录下，链接：https://pan.baidu.com/s/15gvBtnLqrLstmd7u8WgLYg?pwd=n7ai
   提取码：n7ai
7. 下载datax放到服务器上或者电脑本地 下载地址 链接：https://pan.baidu.com/s/1B0b6nevsB7gXOBMvVBqqtA?pwd=ipwu
   提取码：ipwu
8. 打包项目
![img.png](img/img55.png)
9. 拷贝主服务 ruoshui-admin 下的ruoshui-admin.jar 
![img.png](img/img56.png)
10. 运行主jar包
11. 到BigData-UI目录下 执行 npm install --registry=https://registry.npmmirror.com
安装依赖
12. 修改 vue.config.js文件 
![img.png](img/img57.png)
修改为自己的服务段地址
13. npm run dev 启动前端
14. 前端详细部署可以参照若依的部署文档 http://doc.ruoyi.vip/ruoyi-vue/document/hjbs.html#%E5%90%8E%E7%AB%AF%E9%83%A8%E7%BD%B2


#### DataX使用说明
1. 进入系统的 基础建设 模块下的 执行器管理  添加执行器
![img.png](img/img60.png)
2. 修改上面的 ruoshui-datax-executor---->src--->main--->resources下的application.yml配置文件
![img.png](img/img54.png)
注意：服务端的分组名要和你设置的AppName一致
3. 打包 拷贝 ruoshui-datax-executor 下的 ruoshui-datax-executor.jar 执行即可

#### Flink使用说明
1. 打包项目 拷贝 ruoshui-flink-core 下的 flink-streaming-core.jar 重名为 ruoshui-flink-core.jar
2. 放到flink集群的机器目录下 （不需要启动）
3. 设置服务端中 数据开发模块中的 系统设置 即可
![img.png](img/img61.png)
![img.png](img/img62.png)
![img.png](img/img63.png)
这是我的flink机器下的目录结构，对照配置
4. 详细使用方法 请依照 flink-streaming-platform-web 参照地址： https://gitee.com/zhuhuipei/flink-streaming-platform-web

#### 参与贡献
1. Datax-web、flink-streaming-platform-web、datax-cloud的贡献者
2. 心静若水
#### 联系方式
![img.png](img/lxfs.png)

#### 捐赠
![img.png](img/img70.png)
![img_1.png](img/img71.png)
#### 特技
1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
