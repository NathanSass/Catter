# Pre-work - *Catter*

**Catter (For cats who matter)** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Nathan Sass**

Time spent: **24** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Basic error handling and validation for new inputs
* [x] Splash screen on startup
* [x] Background image for main activity
* [x] Makes network calls to the flickr api in order to use images of in the cat detail page. Also able to change the cat background photo

## Video Walkthrough 

Here's a walkthrough of implemented user stories:
![](http://i.giphy.com/3o6gEe9J5sBhEuYIzm.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

- I accidently commented out the setContentView call. That was kind of fun, took me a short while to figure out.
- Is there a way to send whole data objects in an intent? Seems kind of long to have to send each data point by data point.
- When dealing with intents and starting new activities, is there a way to persist some information in memory? I had to pass the item index back and forth even though it is something that I would not want to edit in the edit activity.
- Do I have to copy the chunk of code that adds the image to the app bar to every activity? What is the best pattern to share code between activities?
- Communication with fragments, between activity and communication between nested fragments. Interfaces? Other ideas.

## License

    Copyright 2016 Nathan Sass

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
