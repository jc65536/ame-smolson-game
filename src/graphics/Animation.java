package graphics;

import java.awt.image.*;
import java.util.*;

public class Animation {

    private int frameCount; // counts ticks until frame change
    private int frameDelay;
    private int currentFrame;
    private int animationDirection; // positive or negetive indicates forwards or backwards

    private boolean loop;

    private List<BufferedImage> frames = new ArrayList<>();

    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;

        this.frames.addAll(Arrays.asList(frames));

        // Default state: frame 0, paused, forward, loop
        frameCount = 0;
        this.frameDelay = frameDelay;
        currentFrame = 0;
        animationDirection = 1;
        loop = true;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void restart() {
        currentFrame = 0;
    }

    public BufferedImage getImage() {
        return frames.get(currentFrame);
    }

    public void update() {
        frameCount++;
        if (frameCount >= frameDelay) {
            frameCount = 0;
            currentFrame += animationDirection;
            if (currentFrame > frames.size() - 1) {
                if (loop) {
                    currentFrame = 0;
                } else {
                    currentFrame = frames.size() - 1;
                }
            } else if (currentFrame < 0) {
                if (loop) {
                    currentFrame = frames.size() - 1;
                } else {
                    currentFrame = 0;
                }
            }
        }
    }

}