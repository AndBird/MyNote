## Flutter工程svn忽略文件

```Java
在.idea->workspace.xml中，<component>内新加忽略配置，<ignored/>标签

<component name="ChangeListManager"> 
    <list default="true" id="98591f63-0b13-45b3-9e45-edfa0345c7da" name="Default" comment=""> 
...
...
 </list>
 
 
 <!--工程忽略文件-->
<ignored path=".idea/" />
<ignored path=".gradle/" />
<ignored path="build/" />
<ignored mask="*.iml" />
<ignored mask="*.packages" />
<ignored mask="*.flutter-plugins" />
<ignored mask="*.class" />
<ignored mask="*.log" />


<!--android相关-->
<ignored path="android/.idea/" />
<ignored path="android/gradle/" />
<ignored path="android/captures/" />
<ignored mask="android/gradlew" />
<ignored mask="android/gradlew.bat" />
<ignored mask="android/*.iml" />
<ignored path="android/local.properties" />
<ignored path="modulename/build/" />

<!--iOS/Xcode 相关-->
<ignored mask="ios/**/*.mode1v3" />
<ignored mask="ios/**/*.mode2v3" />
<ignored mask="ios/**/*.moved-aside" />
<ignored mask="ios/**/*.pbxuser" />
<ignored mask="ios/**/*.perspectivev3" />
<ignored path="ios/**/*sync/" />
<ignored mask="ios/**/.sconsign.dblite" />
<ignored mask="ios/**/.tags*" />
<ignored path="ios/**/.vagrant/" />
<ignored path="ios/**/DerivedData/" />
<ignored path="ios/**/Icon?" />
<ignored path="ios/**/Pods/" />
<ignored path="ios/**/.symlinks/" />
<ignored mask="ios/**/profile" />
<ignored path="ios/**/xcuserdata" />
<ignored path="ios/.generated/" />
<ignored mask="ios/Flutter/App.framework" />
<ignored mask="ios/Flutter/Flutter.framework" />
<ignored mask="ios/Flutter/Generated.xcconfig" />
<ignored mask="ios/Flutter/app.flx" />
<ignored mask="ios/Flutter/app.zip" />
<ignored path="ios/Flutter/flutter_assets/" />
<ignored mask="ios/ServiceDefinitions.json" />
<ignored mask="ios/Runner/GeneratedPluginRegistrant.*" />

....
....
</component>


```