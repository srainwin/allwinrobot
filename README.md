# autotestddt
DDT数据驱动+类BBD行为驱动的自动化测试  
java+maven+testng+selenium+sikuli+allure+“testdata from excel”  

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
添加cookies免登录  
从excel获取测试数据  
记录用例运行日志信息  
提供美观的测试报告  
监听失败用例截图  
监听失败用例重跑  
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
pom1.txt：使用nexus私服仓库的配置  
pom2.txt：不使用nexus私服仓库配置  
