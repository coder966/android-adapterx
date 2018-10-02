Changelog
===

##### v0.3.1 (02-10-2018):
* Migrate to androidx artifact.

##### v0.3.0 (31-08-2018):
* The adapter now has a default loading view.
* Add `AdapterX#setNewList`.
* Add `AdapterX#error`.
* Move `OnLoadMoreListener` interface outside `AdapterX` class.
* Pass the adapter reference to `OnLoadMoreListener`.
* Revert the old behaviour of `AdapterX#load`.
* Bug fixes.

##### v0.2.0 (30-08-2018):
* Now if you pass NULL to `AdapterX#load`, it will be considered an error state.

##### v0.1.0 (28-08-2018):
* Initial release

