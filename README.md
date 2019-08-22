# allwinrobot
DDT数据驱动+类BBD行为驱动的自动化测试  
java + maven + testng + selenium + sikuli + allure  

## 主要工具
java:主编程语言（jdk8）  
maven：项目管理构建工具（3.3.9）  
testng：测试框架（6.9.10）  
selenium：web自动化测试工具（3.4.0）  
sikuli：基于图像识别自动化测试工具（1.1.3）  
allure：测试报告工具（2.12.1）  
poi excel：测试数据（4.1.0）  
log4j：日志管理（1.2.16）  
jsonpath：json解析页面对象库（2.2.0）

## 主要功能
selenium常用api封装提供调用ui自动化操作  
sikuli常用api封装提供调用图像识别自动化操作  
可调用autoit的exe脚本进行windows自动化操作  
提供选择ie、chrome、firefox和ghost浏览器进行测试  
支持并行分布式执行用例  
PO模式设计维护用例  
添加cookies免登录  
从excel获取测试数据  
记录用例运行日志信息  
提供美观步级的测试报告  
失败用例可截图  
失败用例可重跑  
DDT数据驱动测试
类BDD行为驱动测试  

# 框架分层
* src/main/java  
  * com.demo.base:存放基类，每个用例都要继承  
  * com.demo.pages:页面对象层，存放每个功能页的元素定位或图像名称（新版使用json文件，旧版java文件样例已归档到backups文件夹中）  
  * com.demo.pagesteps:页面对象操作层，存放每个功能页的操作步骤（每个步骤方法前添加@Step("业务逻辑描述")注解进行类似BDD行为驱动测试）  
  * com.demo.utils:存放封装工具类、配置类、监听类，等等  
* src/main/resources  
  * config:存放配置信息文件  
  * driver:存放各类浏览器驱动  
  * runner:存放配置控制testng运行的xml文件（某些配置参数需在此文件填写，如url等等）  
* src/test/java  
  * com.demo.cases.xxx:xxx功能模块的测试用例，直接调用pagesteps的步骤方法即可  
* src/test/resources  
  * testdata:存放excel测试数据（DDT数据驱动测试）  
  * others  
    * autoit:存放autoit的exe脚本  
    * sikuli:存放sikuli进行图像识别所需的png图像文件  
* target  
  * result  
    * allure-results:存放allure测试报告  
    * logs:存放每天用例运行日志  
    * screenshot:存放失败用例的截图  
    * maven-testng-report:存放testng自带的测试报告  

## pom说明
pom.xml：默认使用了nexus私服仓库  
pom1.txt：使用nexus私服仓库的配置信息，需要时复制到pom.xml  
pom2.txt：不使用nexus私服仓库配置 信息，需要时复制到pom.xml  

## selenium grid分布式测试使用说明
(1)准备多台服务器并能互相网络访问，vm虚拟机要用桥接网络方式，并每台机正确安装各类浏览器  
(2)下载grid包  
selenium-server-standalone-3.141.59.jar  
(3)启动hub节点为控制端（hub机执行），然后访问http://localhost:4444/grid/console  
java -jar selenium-server-standalone-3.141.59.jar -role hub  
(4)建立node节点连接（node机执行），然后刷新访问http://localhost:4444/grid/console  
java -Dwebdriver.chrome.driver="D:/snc/workspace2/autotestddt/src/main/resources/driver/chromedriver.exe" -Dwebdriver.gecko.driver="D:/snc/workspace2/autotestddt/src/main/resources/driver/geckodriver.exe" -jar selenium-server-standalone-3.141.59.jar -role node -host 192.168.1.101 -hub http://192.168.1.100:4444/grid/register -browser browserName=chrome,seleniumProtocol=WebDriver,maxInstances=5,platform=WINDOWS -browser browserName=firefox,seleniumProtocol=WebDriver,maxInstances=5,platform=WINDOWS  
(5)testng.xml/debug.xml填写isRemote和huburl参数值  
(6)使用分布式执行上传文件用例时注意每台机子都要存放相同路径的上传文件  
(7)当hub节点同时也为node节点的时候，启动的浏览器会置底没显示，也就是会被其他已打开的应用遮挡，因此建议运行程序时先把所有应用关闭或最小化，除非浏览器被遮挡运行也不影响功能操作  
(8)当开启selenium grid分布式时，不一定要开启sikuli vnc分布式，除非有使用图像识别的用例运行

## sikuli vnc分布式测试使用说明
(1)准备多台服务器并能互相网络访问  
(2)每台服务器上安装vncserver并启动，options的security要配置prefer on和vnc password  
(3)testng.xml/debug.xml填写isVNC和vncPassword参数值  
(4)testng.xml/debug.xml配置的并发数不能大于分布式服务器总数，因为一个sikuli的screen只对应一个屏幕、鼠标和键盘做操作   
(5)建议每台服务器屏幕分辨率与开发环境屏幕分辨率一致，否则会很容易报错findfailed找不到图像，另外图片的大小、宽高、色差、像素强度等等不一致也容易报错findfailed找不到图像，即同一个图像在不同渲染场景容易findfailed，这是当前sikuli的缺点  
(6)对于上一点使用说明，sikuli作者暂未提供解决方案，查找官网问题论坛中发现有提出过同样问题，作者回复建议是使用image图像集存放多个来自不同渲染环境的目标区域图像，然后迭代访问image图像集的图像直到其中一个找成功即可，作者另一个建议就是利用第三方开源命令行工具ImageMagick进行图像处理但难度较高，本框架决定接受使用第一个建议并封装方法到SikuliUtil.java的pickOneImage(String... images)方法  
(7)当开启sikuli vnc分布式测试时，也必须开启selenium grid分布式测试，因为vnc使用的ip是从selenium grid的session里来获取的  
(8)sikuli输入文字时注意当前输入法的影响  
(9)vnc时paste方法输入中文经常会乱码，不稳定，已向作者提出问题未能解决  
(10)若vnc远程机是node节点而不是hub节点，则paste方法输入的是系统原来粘贴板的内容，已向作者提出问题，但作者回复表示无能为力（I have no idea and no experience with VNC, sorry.）  
(11)鉴于8、9、10三点问题的严重性，建议测试数据输入需求均使用英文，否则sikuli vnc分布式无法处理  
(12)建议需要使用图像识别功能的用例单独一个testng.xml测试套件在本地运行而不用远程vnc  
