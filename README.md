# ActiveLook® FONTSIZES

Description : Try different font sizes in your Augmented Reality ActiveLook® smart glasses
   
### License

```
Licensed under the Apache License, Version 2.0 (the “License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an “AS IS” BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

### Requirements

You will need the following:
- A pair of ActiveLook® glasses
- Android Studio
- An android phone/watch with BLE

Known supported Devices :
- Engo : Cycling & Running action glasses (http://engoeyewear.com/)
- Julbo EVAD : Premium smart glasses providing live data for intense sporting experiences (https://www.julbo.com/en_gb/evad-1)
- Cosmo Connected : GPS & cycling (https://cosmoconnected.com/fr/produits-velo-trottinette/cosmo-vision)

### File to create and add at the root : .env

First, you need to add a file called '.env' at the source of the project. This file will contain only 2 lines :
```
ACTIVELOOK_SDK_TOKEN = ""
ACTIVELOOK_CFG_PASSWORD = 0xDEADBEEF
```

### Main files to modify

The name of the app is defined in the strings.xml file.

* app\src\main\res\layout\content_scrolling.xml
* app\src\main\res\values\strings.xml
* app\src\main\java\com\fontsize\demo\MainActivity.java

In order to get the best performances, the ActiveLookSDK directory should be the latest release from : https://github.com/ActiveLook/android-sdk

### detailed description of this Android application

By using the 2 seekbars you vary the font size from 10 to 32 pixels and the space between 2 lines between 0 an 9 pixels.

For the text, 3 languages are available : French, English and Swedish to test the languages specific characters.

The fonts used are in the file : app\src\main\assets\fonts8.txt

This file is generated under Windows with the python script from : https://github.com/ActiveLook/Config-Generator

I have modified :
* ALookCom\fontAdd  : replaced with the one found here in : ALookCom\fontAdd_fr
* cfgDescriptor\manyFonts directory : new config files

If you want to use a different font or different sizes, do not hezitate to change the config.json file.