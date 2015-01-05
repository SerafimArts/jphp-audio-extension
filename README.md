jphp-awt-extension
==================

AWT Extension for Jphp Language

Available classes:
- DisplayMode
- GraphicsDevice
- GraphicsEnvironment

Exmaple:

```php
<?php
use java\awt\DisplayMode;
use java\awt\GraphicsEnvironment;


$device = GraphicsEnvironment::getDefaultScreenDevice();
$device->setFullScreenWindow($this->getScreen());
$device->setDisplayMode(
  new DisplayMode(1280, 720, 32, DisplayMode::REFRESH_RATE_UNKNOWN)
);
```
