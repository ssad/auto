Test Data
=========

Here you can put your own test data.

Follow the convention:
- name your setup file: &lt;collectionName&gt;-setup.js
- name your teardown file: &lt;collectionName&gt;-teardown.js

If you need data from another files to be processed before your own data implement _dependsOnData()_ method
in your integration test.

See _AccountRepositoryIT_ for example.
