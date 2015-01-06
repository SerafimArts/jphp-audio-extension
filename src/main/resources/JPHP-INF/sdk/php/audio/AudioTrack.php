<?php
namespace php\audio;

use php\audio\controls\BalanceControls;
use php\audio\controls\PanControls;
use php\audio\controls\VolumeControls;
use php\io\Stream;

/**
 * Class AudioTrack
 * @package php\audio
 */
class AudioTrack
{
    /**
     * @var BalanceControls
     */
    public $balance;

    /**
     * @var PanControls
     */
    public $pan;

    /**
     * @var VolumeControls
     */
    public $volume;

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
}