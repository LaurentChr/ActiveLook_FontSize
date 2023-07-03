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

The text to be shown in the glasses need to be treated by a function inluded in app\src\main\java\com\fontsize\demo\MainActivity.java :
```
    public static String international_accent(String text) {
        text = text.replaceAll("Ă","A");
        text = text.replaceAll("Ā","A");
        text = text.replaceAll("Ė","E");
        text = text.replaceAll("Ē","E");
        text = text.replaceAll("Ī","I");
            . . . . .
        text = text.replaceAll("]",")");
        text = text.replaceAll("\\{","(");
        text = text.replaceAll("\\}",")");
        text = text.replaceAll("œ","oe");
        text = text.replaceAll("æ","ae");
        text = text.replaceAll(String.valueOf((char) 0xA0)," "); // Non-breaking space
 
        text = text.replaceAll("á",String.valueOf((char) 2));
        text = text.replaceAll("à",String.valueOf((char) 3));
        text = text.replaceAll("â",String.valueOf((char) 4));
        text = text.replaceAll("ä",String.valueOf((char) 5));
        text = text.replaceAll("ã",String.valueOf((char) 6));
        text = text.replaceAll("å",String.valueOf((char) 7));
        text = text.replaceAll("é",String.valueOf((char) 8));
        text = text.replaceAll("è",String.valueOf((char) 9));
        text = text.replaceAll("ê",String.valueOf((char) 11));
        text = text.replaceAll("ë",String.valueOf((char) 12));
            . . . . .
        text = text.replaceAll("ç",String.valueOf((char) 30));
        text = text.replaceAll("ñ",String.valueOf((char) 31));
        text = text.replaceAll("č",String.valueOf((char) 35)); // #
        text = text.replaceAll("ć",String.valueOf((char) 35)); // #
        text = text.replaceAll("đ",String.valueOf((char) 91)); // [
        text = text.replaceAll("š",String.valueOf(
                (char) 92)+String.valueOf((char) 92)); // \
        text = text.replaceAll("ž",String.valueOf((char) 93)); // ]
        text = text.replaceAll("ą",String.valueOf((char) 94)); // ^
        text = text.replaceAll("ę",String.valueOf((char) 95)); // _
        text = text.replaceAll("ş",String.valueOf((char) 96)); // `
        text = text.replaceAll("ł",String.valueOf((char) 123)); // {
        text = text.replaceAll("ß",String.valueOf((char) 124)); // |
        text = text.replaceAll("€",String.valueOf((char) 125)); // }
        text = text.replaceAll("£",String.valueOf((char) 126)); // ~
        return text;
}
```

If you want to use a different font or different sizes, do not hezitate to change the config.json file.
