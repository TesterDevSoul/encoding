在接口测试中，相信很多人都遇到过 Android 高版本（Android7.0 以上）系统无法抓包的问题。

由于在测试过程中对分析定位问题很不方便，所以就想找开发的同学帮忙，结果开发也说搞不定，那只能自己解决了。

图片

问题分析
问题原因分析如下：

问题：Android6.0 及以下系统可以抓包，而 Android7.0 及以上系统不能抓包；
原因：Android7.0+ 的版本新增了证书验证，所以 App 内不再像原来一样默认信任用户的证书；
参考网上资料得到如下解决方案：

方案一

在 Android 工程目录的 res 底下创建一个 xml 文件夹，然后在内部创建一个名为 “network_security_config.xml”的文件；
<network-security-config>
    <base-config cleartextTrafficPermitted="true">
        <trust-anchors>
            <certificates src="system" overridePins="true" />
            <certificates src="user" overridePins="true" />
        </trust-anchors>
    </base-config>
</network-security-config>
在 AndroidManifest 里的标签中，添加代码:
android:networkSecurityConfig="@xml/network_security_config"
然后重新编译打包即可抓包，这对开发童鞋来说，也很方便。但是，因为测试的是企业微信小程序，想让企业微信的开发人员帮我这么干，简直是白日做梦，更不用说安全等问题...

方案二

找一个低于 6.0 或者等于 6.0 版本的 Android 设备或者模拟器，即可解决。但是考虑到：治标不治本，公司本身就没有这样的设备，再加上找了几个模拟器，都是 Android7.0 版本的，所以此方案，直接选择放弃。

然后只好再去找开发，开发研究了半天，结果过来告诉我，我的 iOS 是可以抓包的啊，Android 的就不知道了，一瞬间我都有点想掀桌子了......

图片

只好再找测试开发同学咨询解决方案：

使用方案一
换个工具抓，例如 Fidder，或者 BurpSuite 等（这个尝试后发现还是不行）
直接去 NG 那里拦截，但是我需要抓取的有一部分是第三方的接口（前方已经高能，此路不通）
最后，还是有点不死心，自己继续搜索各种资料，终于黄天不负有心人，找到了满足条件的最终解决方法。

最终解决方案
Charles + VirtualXposed + JustTrustMe

实现步骤

进入 Github 下载如下两个 Page

VirtualXposed：https://github.com/android-hacker/VirtualXposed
JustTrustMe：https://github.com/Fuzion24/JustTrustMe
第一步
使用如下 adb 命令分别安装两个 pages

adb -s R3J6R19B20004228 inatall VirtualXposed
adb -s R3J6R19B20004228 install JustTrustMe
第二步
安装完成进入 VirtualXposed apk 应用，点击 6 个小点进入设置页面

图片

第三步
进入设置页面，点击模块管理，勾选 JustTrustMe（当然我并没有找到它，可能是我这个版本不需要在手动选择了，安装之后自动识别到了）重启之后我们重新进去设置页面，添加我们需要抓包的应用即可，我这里选择的企业微信做案例。

图片

第四步
点击添加应用，选择需要抓包的软件安装：

图片

配置 Charles 抓包
设置 Charles 代理，此处不再说明，相信设置代理大家能自己解决，手机设置 wifi 里面代理改成手动。输入IP，端口：默认8888，注意手机和电脑在一个 wifi 下就 ok。

第六步
回到 VirtualXposed 上滑解锁，打开我们之前安装的企业微信，则发现 charles 已经成功抓取到安居客的 HTTPS 的数据包：

图片

总结
测试工作中，遇到问题／bug 经常会让人很烦很慌，不知所措。但是，作为一个合格的测试人员，遇到事情，还是要努力做到「泰山崩于前而色不改,麋鹿兴于左而目不瞬」（秀文采～），连开发都放弃了，咱还能保持淡定，方显测试英雄本色！🆒

技术进阶没有捷径，唯有一步步积累，踏坑填坑坚持走下去。这次的问题虽几经波折，但我最终搞定之后，开发看我的眼神都不一样了，以后提 bug 也更加有说服力了。😄

