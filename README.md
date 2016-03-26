# Kusa [![](https://jitpack.io/v/chooblarin/kusa.svg)](https://jitpack.io/#chooblarin/kusa)

View library to draw the 草 chart (know as calendar heat map).

<p align="center">
  <img src="assets/screenshot.png">
</p>

# Usage

Add a `KusaView` to your view hierarchy. You should specify the height of it.

```
<com.chooblarin.kusa.KusaView
    android:id="@+id/kusa_view"
    android:layout_width="match_parent"
    android:layout_height="120dp" />
```

Look at the [sample](https://github.com/chooblarin/kusa/blob/master/sample/src/main/java/com/chooblarin/kusa/demo/MainActivity.java)

# Install

This can be found on JitPack: https://jitpack.io/#chooblarin/kusa

Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
      ...
      maven { url "https://jitpack.io" }
    }
}
```

Add the dependency
```
dependencies {
    compile 'com.github.chooblarin:kusa:0.0.2'
}
```

# License

Copyright 2016 chooblarin.

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
