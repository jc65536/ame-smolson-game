import java.awt.image.*;
import java.util.*;

public class Animation {
    
    private int frameCount;         // Counts ticks until frame change
    private int frameDelay;         // frame delay
    private int currentFrame;       // animations current frame
    private int animationDirection; // animation direction (i.e forward or backward)

    private boolean playing;
    private boolean loop;

    private List<BufferedImage> frames = new ArrayList<>(); // Arraylist of frames
    private String label;

    public Animation(String label, BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        playing = false;
        this.label = label;

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

    public String getLabel() {
        return label;
    }

    public boolean playing() {
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
        if (playing) {
            frameCount++;
            if (frameCount >= frameDelay) {
                frameCount = 0;
                currentFrame += animationDirection;
                if (currentFrame > frames.size() - 1) {
                    currentFrame = 0;
                    if (!loop)
                        playing = false;
                } else if (currentFrame < 0) {
                    currentFrame = frames.size() - 1;
                    if (!loop)
                        playing = false;
                }
            }
        }
    }

}