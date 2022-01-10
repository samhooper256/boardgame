package minigames.archery;

import java.util.*;

import javafx.geometry.Point2D;
import javafx.util.Duration;

/** All coordinates are ideal. The points on the path are centers. */
public class TargetPath {
	
	private final Duration delay;
	private final double speed;
	private final List<Point2D> points;
	
	public TargetPath(Duration delay, double speed, Point2D... points) {
		this.delay = delay;
		this.speed = speed;
		this.points = new ArrayList<>();
		Collections.addAll(this.points, points);
	}
	
	public Duration delay() {
		return delay;
	}
	
	public List<Point2D> pointsUnmodifiable() {
		return Collections.unmodifiableList(points);
	}
	
	public Point2D point(int index) {
		return points.get(index);
	}
	
	public double speed() {
		return speed;
	}
	
	public int stops() {
		return points.size();
	}
	
}
