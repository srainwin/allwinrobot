# autotestddt
DDT数据驱动+类BBD行为驱动的自动化测试  
java + maven + testng + selenium + sikuli + allure  

## 主要工具
java:主编程语言（jdk8）  
maven：项目管理构建工具（3.3.9）  
testng：测试框架（6.9.10）  
selenium：web自动化测试工具（3.4.0）  
sikuli：基于图像识别自动化测试工具（1.1.2）  
allure：测试报告工具（2.12.1）  
poi excel：测试数据（4.1.0）  
log4j：日志管理（1.2.16）  

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
  * com.demo.pages:页面对象层，存放每个功能页的元素定位或图像名称  
  * com.demo.pagesteps:页面对象操作层，存放每个功能页的操作步骤（每个步骤方法前添加@Step("业务逻辑描述")注解进行类似BDD行为驱动测试）  
  * com.demo.utils:存放封装工具类、配置类、监听类，等等
* src/main/resources  
  * config:存放配置信息文件  
  * driver:存放各类浏览器驱动  
  * runner:存放配置控制testng运行的xml文件  
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

## selenium grid分布式使用说明
(1)准备多台机子并能互相网络访问，vm虚拟机要用桥接网络方式，并每台机正确安装各类浏览器  
(2)下载grid包  
selenium-server-standalone-3.141.59.jar  
(3)启动hub节点为控制端（hub机执行），然后访问http://localhost:4444/grid/console  
java -jar selenium-server-standalone-3.141.59.jar -role hub  
(4)建立node节点连接（node机执行），然后刷新访问http://localhost:4444/grid/console  
java -Dwebdriver.chrome.driver="D:/snc/workspace2/autotestddt/src/main/resources/driver/chromedriver.exe" -Dwebdriver.gecko.driver="D:/snc/workspace2/autotestddt/src/main/resources/driver/geckodriver.exe" -jar selenium-server-standalone-3.141.59.jar -role node -host 192.168.1.101 -hub http://192.168.1.100:4444/grid/register -browser browserName=chrome,seleniumProtocol=WebDriver,maxInstances=5,platform=WINDOWS -browser browserName=firefox,seleniumProtocol=WebDriver,maxInstances=5,platform=WINDOWS  
(5)testng.xml/debug.xml填写isRemote和huburl参数值  
(6)使用分布式执行上传文件用例时注意每台机子都要存放相同路径的上传文件

## 其他说明
sikuli api仅作为辅助操作测试用，主要使用selenium api，因为sikuli图像识别这块缺少较好的断言方案，而且sikuli是对整个屏幕为对象操作而一台机只有一个屏幕导致无法使用并行执行用例
