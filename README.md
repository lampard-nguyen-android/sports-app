# **Sports App Sample**
Sports App Sample is an Android App for a hypothetical sports event

**TECHSTACKS**

- Language: Kotlin
- Architecture: MVVM
- Libraries & Dependences: 
    - UI: Navigation, RecycleView, ViewBinding
    - Support MVVM Architecture: lifecycle ViewModel, Kotlin Flow
    - Coroutine, Kotlin Flow: Executes asynchronously
    - Interact Restful API: Retrofit, OkHttp, Gson
    - Dependency Injection: Dagger-Hilt, 
    - Testing: JUnit Test, Mockito, AndroidX Test Core

**PROJECT STRUCTURE**

- Project structure follows MVVM architecture: 

    - Data, Service, Repository: help interact Restful API
    - DI: support dependency injection using dagger-Hilt
    - Data: come from service, remote storage
    - Entity: come from database local storage
    - Domain: data model to update UI state
    - Other: Reminder Manager & Dates/ Notification Utils
    - Test: Unit test for viewmodel 
    
**FUTURE OF WORK**
- Support refresh/ no data/ no internet
- Support caching data for teams/ matches
- Show team logo for match, it can use from cache
- Support add/ remove/ edit option for reminder of each match
- Add UI test & check UI responsive on foldable/ tablet

**RECORDING**
- https://drive.google.com/file/d/1-LK2DmQovfg6-P-wSEvy8EW6wl_9YIr2/view?usp=sharing
