#  <img src="/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png" alt="Covid Stats logo" width="40" height="40" align="center"/> Covid-Stats-App

Covid Stats is an app that enables users to track global Covid stats. There are 9
different stats that can be viewed including more interesting ones like fatality rates.
The data is sourced from Johns Hopkins CSSE and is provided by the [Covid API](https://covid19api.com/) as JSON. 

<a href="http://www.youtube.com/watch?feature=player_embedded&v=pQ_T457ort4" target="_blank"><img src="http://img.youtube.com/vi/pQ_T457ort4/0.jpg" 
alt="App Showcase" width="400" height="306" border="10" /></a>

## Technical Details

* The app uses MVVM-architecture.
* The entire navigation is implemented via the Navigation Components library.
* Retrofit with GSON-Parsing is used for the network requests.
* The app uses R8 to make the APK smaller. 
* Don't mess with the ProGuard rules. This might introduce bugs in the GSON parsing. 
