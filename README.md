# Android App for making and viewing virtual exhibitions
This application made with Java in Android Studio, it's my first Android project. With this app you can create "exhibitions": full-screen images with points on them, tap one of the points and you will see information (few different layouts can be used), which can include rich text, images, audio or video.

This is not only for exhibitions in the literal sense of the word (not only museum kind), but you can also make the family album, show your goods, make some map - much different stuff, anything with the fullscreen image at the base.

You're absolutely free to use it as you want, make any changes and add issues, but don't forget it's my first Android app, so its code may be a total mess.

## How to use and what can be done with it 
* Build with Android Studio and install apk to the Android device
* Create new Exhibition and give it some name (end-point user will not see its name, so it'just for you)
* Add the first Scene, give it a name, background picture and description (if needed)
* Add one Point to the Scene and give it some content (choose layout and fill text fields)
* Add more Points 
* When finished with this Scene press Back and add more Scenes to the current Exhibition
* You can edit existing scenes by pressing three dots button and then Edit button
* When you done, try to run your Exhibition-project, go back to Exhibition list, press three dots button, then Run
* That's it!

Some screenshots of process:
![my_image_2](https://raw.githubusercontent.com/Hexronimo/exhibition/master/Demo2.jpg)

All Exhibitions you made will be saved to Android/data/ru.hexronimo.exhibition/files/ExhibitionApp as .ser serializable Java objects. But content (images, audio, video) is just linked, so if you move or delete any used image in the device's filesystem an app may crash. 

## What was learned
As I said it's my first Android app and I did it just to learn things. Let me be honest, after web-development and backend this was a pain in the ass. I guess I needed to start development by studying Material Design, but I dont want to (and I still don't)! This development is so Api-depended, everything scroll not as expected, floats not as expected, I don't even know how to add normal responsiveness (to tablet app!!!) to it. I read a ton of topics at StackOverflow just to made some really basic things (well, I mean they would be basic at the web-frontend for example). I'm still not sure I understand everything right about Activity, Intent, layouts etc... and the fact that this App works is black magic. 
There is one good thing about it: I really-really-really want to do and learn tasks about algorithms now! I need to make some command-line app or up my level at Leetcode, do something where there is no place for a fancy design.

## Idea (pre-development)
I have few old cheap tablets at my current workplace and think it would be nice to give them second life. So I wanted to make exibition viewer for them, something very simple and universal, that any non-technical person can use later and supply with a new content.

It's not VR or 3D Panorama, because from my experience it's not what people need in everyday use. Usually we need "tap this point -> read about it" programm and that's it. 

Also important thing - end-point user (not developer and content-manager) shouldn't be able to close it or make some harm. 
![my_image_1](https://raw.githubusercontent.com/Hexronimo/exhibition/master/idea.jpg)


## Here will be photos from real usage
I like images
