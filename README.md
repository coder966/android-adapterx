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
For full working example, see the demo module.

Normally, if you use the regular Adapter<VH> you will have something similar to this:
```java
// prepare the recycler view
RecyclerView recyclerView = findViewById(R.id.recycler_view);
recyclerView.setLayoutManager(new LinearLayoutManager(context));

// prepare the adapter
MyAdapter adapter = new MyAdapter(myList);

// set the adapter to the recycler view
recyclerView.setAdapter(adapter);
```

To use AdapterX, you must set two things:

* The loading view: the view that will be shown at the very end of the list when the RecyclerView is in loading state.
* The OnLoadMoreListener: the callback that is invoked every time when the user scrolls down and approaches the end of the list.

Example:

```java
// NOTE: this must be done before setting the adapter to the recycler view.

adapter.setLoadingView(R.layout.loading);
adapter.setOnLoadMoreListener(lastItem -> {
	/*
	Perform DB/API call to get more items.
	IF YOU NEED, you can use the provided reference "lastItem" to determine which items to load.
	*/

	// when you get your new list of items, call load method
	// if you pass null or empty list, AdapterX will figure out that the dataset has been completely loaded and will disable the loading feature automatically.
	adapter.load(moreDataList);
});
```
The above segment of code uses Java's Lambda Expressions. If you are not familiar with JDK8 new features, you can always use the old style.

More optional settings
---
AdapterX can pre-fetch data to improve user experience. The default pre-fetch distance is 5 items. You can change that by:

```java
adapter.setPrefetchThreshold(10);
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
