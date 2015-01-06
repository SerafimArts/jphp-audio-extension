<?php
namespace php\audio\controls;

/***
 * Class AbstractAudioControls
 * @package php\audio\controls
 */
abstract class AbstractAudioControls
{
    /**
     *
     */
    private function __construct() {}

    /**
     * @return float
     */
    public function getValue() { return 0.0; }

    /**
     * @param float $value
     * @return null
     */
    public function setValue($value = 0.0) { return null; }

    /**
     * @return float
     */
    public function getMaximum() { return 0.0; }

    /**
     * @return float
     */
    public function getMinimum() { return 0.0; }
}