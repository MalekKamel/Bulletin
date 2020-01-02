
# Bulletin 
An abstraction for alert messages in android.

More and more you need to report bulletins (messages) to the user and you spread its code everywhere. And sometimes you ask yoursef, what if I want to avoid duplicate bulletins at the same time? what if I want to queue bulletins? what if I want to apply default configurations to each bulletin only in one place? what if I want to manage all bulletins?.....  👿 👿

**Bulletin** can answer all you queustions!  🙏 🙏

<img src="https://github.com/ShabanKamell/Bulletin/blob/master/blob/master/raw/diagram4.png" height="600">

## Installation

#### Gradle:
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
        implementation 'com.github.ShabanKamell:Bulletin:x.y.z'
}
```

(Please replace x, y and z with the latest version numbers: [![](https://jitpack.io/v/ShabanKamell/Bulletin.svg)](https://jitpack.io/#ShabanKamell/Bulletin))

## Bulletins
Any widget implements [Bulletin Interface](#bulletin-interface) is a Bulletin. There're 7 predefined widgets in the library:
- [ ] **InfoDialog**: a concrete implementaion of `BulletinDialog`
- [ ] **RetryDialog**: a concrete implementaion of `BulletinDialog`
- [ ] **LoadingDialog**: a concrete implementaion of `BulletinDialog`
- [ ] **InfoSheet**: a concrete implementaion of `BulletinSheet`
- [ ] **RetrySheet**: a concrete implementaion of `BulletinSheet`
- [ ] **StandardFlashBar**: a concrete implementaion of `BulletinFlashBar`
- [ ] **StandardToast**: a concrete implementaion of `BulletinToast`

## Bulletin Interface
`Bulletin` is implemented by all widgets of the library. If you want to create your custom bulletin, you must implement this interface.

``` kotlin
interface Bulletin {
    val name: String
    val content: String
    fun showBulletin(activity: FragmentActivity?)
    fun dismiss()
}
```
## Alertable Interface
Alertable interface contains a group of default functions that make it easy to show any predefined `Bulletin`. If you want to make all these functions available for your class, just implement it. The interface is a composite of interfaces for each bulletin. Take a look at [The code](https://github.com/ShabanKamell/Bulletin/blob/master/lib/src/main/java/com/sha/bulletin/Alertable.kt) to see all functionalities.

``` kotlin
interface Alertable:
        InfoDialogAlertable,
        InfoSheetAlertable,
        RetryDialogAlertable,
        RetrySheetAlertable,
        ToastAlertable,
        LoadingDialogAlertable,
        FlashBarAlertable
```
## BulletinManager
It's the brain of the library that's responsible for showing/hiding bulletins, storing the current state of each bulletin and checking if a bulletin is displayed. Take a look at [The code](https://github.com/ShabanKamell/Bulletin/blob/master/lib/src/main/java/com/sha/bulletin/BulletinManager.kt) to see all functionalities.

## Custom Bulletins
As mentioned in [Bulletin Interface](#bulletin-interface), you can create your custom bulletin by implementing [Bulletin] interface. Alternatively, you can extend abstract widget like `BulletinDialog` and implement your customization.



### 🛡 License
<details>
    <summary>
        click to reveal License
    </summary>
    
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

</details>
