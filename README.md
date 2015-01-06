jphp-audio-extension
==================

Audio Extension for Jphp Language

Available classes:
- AudioDevice
- AudioSystem
- AudioTrack

Exmaple:

```php
<?php
use php\audio\AudioSystem;
use php\audio\AudioDevice;
use php\audio\AudioTrack;

$devices = AudioSystem::getDevices(AudioDevice::SPEAKER);
var_dump($devices);


$track = new AudioTrack(Stream::of('res://audio/1.mp3'));
$track->volume->setValue($track->volume->getMaximum() / 2);
$track->balance->setValue($track->balance->getMinimum());
$track->play();
```
