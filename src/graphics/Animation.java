package graphics;

import java.awt.image.*;
import java.util.*;

public class Animation {

    private int frameCount; // counts ticks until frame change
    private int frameDelay;
    private int currentFrame;
    private int animationDirection; // positive or negetive indicates forwards or backwards

    private boolean playing;
    private boolean loop;

    private List<BufferedImage> frames = new ArrayList<>();

    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        playing = false;

        this.frames.addAll(Arrays.asList(frames));

        // Default state: frame 0, paused, forward, no loop
        frameCount = 0;
        this.frameDelay = frameDelay;
        currentFrame = 0;
        animationDirection = 1;
        loop = false;
    }

    public void setLoop(boolean looping) {
        this.loop = looping;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean loops() {
        return loop;
    }

    public void start() {
        playing = true;
    }

    public void stop() {
        playing = false;
    }

    public void restart() {
        currentFrame = 0;
    }

    public BufferedImage getImage() {
        return frames.get(currentFrame);
    }

    public void update() {
        if (!playing)
            return;

        frameCount++;
        if (frameCount >= frameDelay) {
            frameCount = 0;
            currentFrame += animationDirection;
            if (currentFrame > frames.size() - 1) {
                if (loop) {
                    currentFrame = 0;
                } else {
                    playing = false;
                    currentFrame = frames.size() - 1;
                }
            } else if (currentFrame < 0) {
                if (loop) {
                    currentFrame = frames.size() - 1;
                } else {
                    playing = false;
                    currentFrame = 0;
                }
            }
        }
    }

}