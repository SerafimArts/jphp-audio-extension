<?php
namespace php\audio;

use php\io\Stream;

/**
 * Class AudioTrack
 * @package php\audio
 */
class AudioTrack
{
    /**
     * Create new audio track
     * @param Stream $stream
     * @throws \Exception
     */
    public function __construct(Stream $stream) { throw new \Exception; }


    /**
     * Play audio
     * @return $this
     * @throws \Exception
     */
    public function play()
    {
        if (false) throw new \Exception;
        return $this;
    }

    /**
     * Pause audio
     * @return $this
     */
    public function pause() { return $this; }

    /**
     * Toggle play\pause methods
     * @return $this
     * @throws \Exception
     */
    public function toggle()
    {
        if (false) throw new \Exception;
        return $this;
    }

    /**
     * Stops audio and destroy track
     * @return $this
     */
    public function stop() { return $this; }


    /**
     * Mute audio
     * @return $this
     */
    public function mute() { return $this; }

    /**
     * Unmute audio
     * @return $this
     */
    public function unmute() { return $this; }

    /**
     * Currently in play status
     * @return bool
     */
    public function isPlayed() { return false; }

    /**
     * Currently in paused status
     * @return bool
     */
    public function isPaused() { return false; }

    /**
     * Track completed
     * @return bool
     */
    public function isComplete() { return false; }

    /**
     * Return track position
     * @return int
     */
    public function getPosition() { return 0; }

    /**
     * Set track volume
     * @param int $value
     * @return $this
     */
    public function setVolume($value) { return $this; }

    /**
     * Return track volume
     * @return float
     */
    public function getVolume() { return 0.0; }

    /**
     * Return minimum volume
     * @return float
     */
    public function getVolumeMinimum() { return 0.0; }

    /**
     * Return maximum volume
     * @return float
     */
    public function getVolumeMaximum() { return 0.0; }

    /**
     * Set track pan
     * @param float $value
     * @return $this
     */
    public function setPan($value) { return $this; }

    /**
     * Return track pan
     * @return float
     */
    public function getPan() { return 0.0; }

    /**
     * Return minimum pan
     * @return float
     */
    public function getPanMinimum() { return 0.0; }

    /**
     * Return maximum pan
     * @return float
     */
    public function getPanMaximum() { return 0.0; }

    /**
     * Set track balance
     * @param float $value
     * @return $this
     */
    public function setBalance($value) { return $this; }

    /**
     * Return track balance
     * @return float
     */
    public function getBalance() { return 0.0; }

    /**
     * Return minimum balance
     * @return float
     */
    public function getBalanceMinimum() { return 0.0; }

    /**
     * Return maximum balance
     * @return float
     */
    public function getBalanceMaximum() { return 0.0; }

}
