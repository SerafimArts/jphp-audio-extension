<?php
namespace php\audio;

/**
 * Class AudioSystem
 * @package php\audio
 */
class AudioSystem
{
    private function __construct() {}

    /**
     * @param null|int|AudioDevice::TYPE $supported
     * @return array
     */
    public static function getDevices($supported = null)
    {
        return [];
    }

}