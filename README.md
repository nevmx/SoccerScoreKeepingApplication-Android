# SoccerScoreKeepingApplication-Android
## Description
This is an Android port of the [SoccerScoreKeeper](github.com/ahmadowais123/SoccerScoreKeepingApplication) application. This was developed using Android Studio and built for API 23. The app was tested on phones and tablets.

## Main Functions
* Scorekeeping Modes
* Team and Player Analysis Modes
* Storing stats in an XML file
* Uploading and Downloading Stats to a remote location on the internet

## Libraries Used
* X-Stream (see note)
* Apache HttpComponents (uploading and XML file through a POST request (multipart upload)) (see note)
* Google Volley library (downloading an XML file)

## Notes
  X-Stream serializes objects in an alphabetical order in the XML string. This made working with the XML file more complicated than needed to be. A fix was used to restore the usual order, where the objects are serialized in the order that they were declared in.
  Apache HttpComponents is no longer compatible to use on Android API 21 and up. A patched modified library was used that restores the functionality of HttpComponents under Android.
