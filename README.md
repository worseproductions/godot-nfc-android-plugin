# Godot NFC Android Plugin
A simple NFC plugin for android games built with godot. Currently only configured to work with NDEF records.

## Usage
**Note:** [Android Studio](https://developer.android.com/studio) is the recommended IDE for
developing Godot Android plugins. 
You can install the latest version from https://developer.android.com/studio.

### Building the configured Android plugin
- In a terminal window, navigate to the project's root directory and run the following command:
```
./gradlew assemble
```
- On successful completion of the build, the output files can be found in
  [`plugin/build/addon`](plugin/build/addon)

#### Tips
Additional dependencies added to [`plugin/build.gradle.kts`](plugin/build.gradle.kts) should be added to the `_get_android_dependencies`
function in [`plugin/export_scripts_template/export_plugin.gd`](plugin/export_scripts_template/export_plugin.gd).

##### Simplify access to the exposed Java / Kotlin APIs

To make it easier to access the exposed Java / Kotlin APIs in the Godot Editor, it's recommended to 
provide one (or multiple) gdscript wrapper class(es) for your plugin users to interface with.

For example:

```
class_name PluginInterface extends Object

## Interface used to access the functionality provided by this plugin

var _plugin_name = "GDExtensionAndroidPluginTemplate"
var _plugin_singleton

func _init():
	if Engine.has_singleton(_plugin_name):
		_plugin_singleton = Engine.get_singleton(_plugin_name)
	else:
		printerr("Initialization error: unable to access the java logic")

## Shows a 'Hello World' toast.
func helloWorld():
	if _plugin_singleton:
		_plugin_singleton.helloWorld()
	else:
		printerr("Initialization error")

```

