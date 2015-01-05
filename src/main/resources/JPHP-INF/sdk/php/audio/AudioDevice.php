<?php
namespace php\audio;

/**
 * Class AudioDevice
 * @package php\audio
 */
class AudioDevice
{
    const MICROPHONE      = 2;
    const LINE_IN         = 4;
    const COMPACT_DISC    = 8;
    const SPEAKER         = 16;
    const HEADPHONE       = 32;
    const LINE_OUT        = 64;



    private function __construct() {}


    /**
     * Return device name
     * @return string
     */
    public function getName() { return ''; }


    /**
     * Return device vendor
     * @return string
     */
    public function getVendor() { return ''; }


    /**
     * Return device version
     * @return string
     */
    public function getVersion() { return ''; }

    /**
     * Return device description
     * @return string
     */
    public function getDescription() { return ''; }


    /**
     * Check supported type
     * @param int|AudioDevice::TYPE $type
     * @return bool
     */
    public function isSupported($type) { return false; }
}