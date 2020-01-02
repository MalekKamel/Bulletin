
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
Any widget implements [Bulletin Interface](#bulletin-interface) is a Bulletin. There're 8 predefined widgets in the library:

|         **Name**                 |                        **Description**                         |
| -------------------------------- | ---------------------------------------------------------------|
| **InfoDialog**                   | A concrete implementaion of `BulletinDialog`                   |
| **RetryDialog**                  | A concrete implementaion of `BulletinDialog`                   |
| **LoadingDialog**                | A concrete implementaion of `BulletinDialog`                   |
| **InfoSheet**                    | A concrete implementaion of `BulletinSheet`                    |
| **RetrySheet**                   | A concrete implementaion of `BulletinSheet`                    |
| **StandardFlashBar**             | A concrete implementaion of `BulletinFlashBar`                 |
| **StandardToast**                | A concrete implementaion of `BulletinToast`                    |
| **StandardSnackbar**             | A concrete implementaion of `BulletinSnackbar`                 |

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

## BulletinManager
It's the brain of the library that's responsible for showing/hiding bulletins and managing the state of each bulletin. Take a look at [BulletinManager](https://github.com/ShabanKamell/Bulletin/blob/master/lib/src/main/java/com/sha/bulletin/BulletinManager.kt) to see all functionalities.

## Alertable Interface
Alertable interface contains a group of default functions that make it easy to show any predefined `Bulletin`. If you want to make all these functions available for your class, just implement it. The interface is a composite of interfaces for each bulletin. Take a look at [Alertable](https://github.com/ShabanKamell/Bulletin/blob/master/lib/src/main/java/com/sha/bulletin/Alertable.kt) to see all functionalities.

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

In case you don't need all functions, you can implement any interface that Alertable inhertits.

## Duplicate Strategy
What if there are 2 or more netwrok responses display the same content in a dialog and you don't need to show all dialogs with the same content? OR in another words you want to ignore the dialog if there's a dialog with the same content displayed?

[DuplicateStrategy](#duplicate-strategy-interface) provides the solution:

``` kotlin
  BulletinConfig.duplicateStrategy = ContentDuplicateStrategy()
```

The previous line tells **Bulletin** to ignore the new bulletin as long as a bulletin with the same content is displayed.

### Duplicate Strategy Interface:

``` kotlin
interface DuplicateStrategy {
    var onIgnoreStrategy: IgnoreDuplicateStrategy
    fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean
}
```

BUT what happens to the ignored bulletins? see [Ignore Duplicate Strategy](#ignore-duplicate-strategy)  🤔 🤔

There are multiple predefined implementaions for `DuplicateStrategy` you can find them in the next section. However, you can define your own custom strategy.

### Predefined Duplicate Strategies

|         **Name**                 |                        **Description**          | **Default IgnoreDuplicateStrategy** |
| -------------------------------- | --------------------------------------------------------------------- | ------------- |
| **DefaultDuplicateStrategy**     | Allows any diplication                                                |   DROP        |
| **NameDuplicateStrategy**        | Ignore if a Bulletin with the same **NAME** is displayed.             |   QUEUE       |
| **ContentDuplicateStrategy**     | Ignore if a Bulletin with the same **CONTENT** is displayed.          |   DROP        |
| **NameContentDuplicateStrategy** | Ignore if a Bulletin with the same **NAME & CONTENT** is displayed.   |   DROP        |
| **SingleDuplicateStrategy**      | Display a single Bulletin at a time    .                              |   QUEUE       |


### Ignore Duplicate Strategy

If a `Bulletin` has been ignored bucause it's a duplicate one, you can define 1 of 3 behaviors for the ignored bulletin:

- [ ] Drop: The bulletin will be dropped and won't be displayed forever.
- [ ] Queue: The bulletin will be queued, and will be displayed once it's the first bulletin in the queue.
- [ ] Try Queue: The bulletin will be queued only if there's any [Duplicate Strategy](#duplicate-strategy) allows queuing the bulletin.

These behavioiors are defined in[IgnoreDuplicateStrategy](#ignoreDuplicateStrategy-enum).

#### IgnoreDuplicateStrategy Enum
`IgnoreDuplicateStrategy` contains the ignoring behaviors that can be defined in in [DuplicateStrategy Inteface](#duplicate-strategy-interface).

``` kotlin
enum class IgnoreDuplicateStrategy { DROP, QUEUE, TRY_QUEUE }
```

## Queue Strategy
What if you need to show 2 bulletins or more in a sequntial order? In another worders, what if you want to show each bulletin after dismissing the previous one?

[`QueueStrategy`](#queue-strategy-interface) interface provides a solution for queuing problem:

``` kotlin
BulletinConfig.queueStrategies { +SheetQueueStrategy() }
```

### Queue Strategy Interface:

``` kotlin
interface QueueStrategy {
    fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean
}
```

There are multiple predefined implementaions for `QueueStrategy` you can find them in the next section. However, you can define your own custom strategy.

### Predefined Queue Strategies

|         **Name**                 |                        **Description**                         |
| -------------------------------- | ---------------------------------------------------------------|
| **NoneQueueStrategy**            | Don't queue any bulletin. This is the default behavior         |
| **AllQueueStrategy**             | Queue all bulletins                                            |
| **DialogQueueStrategy**          | Queue if a `BulletinDialog` is displayed.                      |
| **SheetQueueStrategy**           | Queue if a `BulletinSheet` is displayed.                       |
| **FlashbarQueueStrategy**        | Queue if a `BulletinFlashbar` is displayed.                    |
| **SnackbarQueueStrategy**        | Queue if a `BulletinSnackar` is displayed.                     |
| **ToastQueueStrategy**           | Queue if a `BulletinToast` is displayed.                       |

## Custom Bulletins
As mentioned in [Bulletin Interface](#bulletin-interface), you can create your custom bulletin by implementing [Bulletin] interface. Alternatively, you can extend abstract widget like `BulletinDialog` and implement your customization. see [MyCstomLadingDialog](https://github.com/ShabanKamell/Bulletin/blob/master/sample/src/main/java/com/sha/sample/MyCustomLoadingDialog.kt)

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
