# AdapterX

An Android RecyclerView Adapter that adds the feature "Load More".

Installation
---
Please use whatever last version is.
```gradle
implementation 'net.coder966.android:adapterx:0.1.0'
```

Usage
---
For a full working example, see the demo module.

* Extend `AdapterX<T>`. `T` is the type of individual items in your list.
* To enable "Load More" feature, you must set the `OnLoadMoreListener`.

```java
// NOTE: this must be done before setting the adapter to the recycler view.

// optional but mandatory if you want to enable "Load More" feature.
adapter.setOnLoadMoreListener((adapterx, lastItem) -> {
	/*
	Perform DB/API call to get more items.
	IF YOU NEED, you can use the provided reference "lastItem" to determine which items to load.
	*/

	// when you get your new list of items, call load method
	adapterx.load(moreDataList);
});

// The above segment of code uses Java's Lambda Expressions. If you are not familiar with JDK8 new features, you can always use the old style.
```

More optional settings
---
* AdapterX can pre-fetch data to improve user experience. The default pre-fetch distance is 5 items.

```java
adapter.setPrefetchThreshold(10);
```

* To set a custom loading view

```java
adapter.setLoadingView(R.layout.loading);
```

License
---
```
Copyright 2018 Khalid H. Alharisi

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
