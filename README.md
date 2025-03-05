# Overview

This sample allows flexible display of web pages by specifying a particular URL in a designated location. Pull-to-refresh is specified on the app side, enabling users to reload the page.

## How to Specify the URL

Modify the `"Set your URL here"` part of `loadUrl` on line 41 of `FirstFragment.kt` to your desired URL.

For example, if you want to display Google's web page, update it as follows:

### Before modification:

```kotlin
loadUrl("Set your URL here")
```

### After modification:

```kotlin
loadUrl("https://www.google.com")
```
