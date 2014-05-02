package com.turtleGames.vandal.classes;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Rectangle3D extends Rectangle {

	private static final long serialVersionUID = 4046064488079904698L;

	public float x, y, z;
	public float width, height, depth;

	/** Constructs a new rectangle with all values set to zero */
	public Rectangle3D() {

	}

	/**
	 * Constructs a new rectangle with the given corner point in the bottom left
	 * and dimensions.
	 * 
	 * @param x
	 *            The corner point x-coordinate
	 * @param y
	 *            The corner point y-coordinate
	 * @param z
	 *            The corner point z-coordinate
	 * @param width
	 *            The width
	 * @param height
	 *            The height
	 * @param depth
	 *            The depth
	 */
	public Rectangle3D(float x, float y, float z, float width, float height,
			float depth) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.width = depth;
	}

	public Rectangle3D(Rectangle rect) {
		x = rect.x;
		y = rect.y;
		z = rect.y;
		width = rect.width;
		height = rect.height;
		width = rect.width;
	}

	/** @return the x-coordinate of the bottom left corner */
	public float getX() {
		return x;
	}

	/**
	 * Sets the x-coordinate of the bottom left corner
	 * 
	 * @param x
	 *            The x-coordinate
	 * @return this rectangle for chaining
	 */
	public Rectangle3D setX(float x) {
		this.x = x;

		return this;
	}

	/** @return the y-coordinate of the bottom left corner */
	public float getY() {
		return y;
	}

	/**
	 * Sets the y-coordinate of the bottom left corner
	 * 
	 * @param y
	 *            The y-coordinate
	 * @return this rectangle for chaining
	 */
	public Rectangle3D setY(float y) {
		this.y = y;

		return this;
	}

	/** @return the z-coordinate of the bottom left corner */
	public float getZ() {
		return z;
	}

	/**
	 * Sets the z-coordinate of the bottom left corner
	 * 
	 * @param z
	 *            The z-coordinate
	 * @return this rectangle for chaining
	 */
	public Rectangle3D setZ(float z) {
		this.z = z;

		return this;
	}

	/** @return the width */
	public float getWidth() {
		return width;
	}

	/**
	 * Sets the width of this rectangle
	 * 
	 * @param width
	 *            The width
	 * @return this rectangle for chaining
	 */
	public Rectangle3D setWidth(float width) {
		this.width = width;

		return this;
	}

	/** @return the height */
	public float getHeight() {
		return height;
	}

	/**
	 * Sets the height of this rectangle
	 * 
	 * @param height
	 *            The height
	 * @return this rectangle for chaining
	 */
	public Rectangle3D setHeight(float height) {
		this.height = height;

		return this;
	}

	/** @return the depth */
	public float getDepth() {
		return width;
	}

	/**
	 * Sets the depth of this rectangle
	 * 
	 * @param depth
	 *            The width
	 * @return this rectangle for chaining
	 */
	public Rectangle3D setDepth(float depth) {
		this.width = depth;

		return this;
	}

	/**
	 * return the Vector3 with coordinates of this rectangle
	 * 
	 * @param position
	 *            The Vector3
	 */
	public Vector3 getPosition(Vector3 position) {
		return position.set(x, y, z);
	}

	/**
	 * Sets the x, y and z-coordinates of the bottom left corner from vector
	 * 
	 * @param position
	 *            The position vector
	 * @return this rectangle for chaining
	 */
	public Rectangle3D setPosition(Vector3 position) {
		this.x = position.x;
		this.y = position.y;
		this.z = position.z;

		return this;
	}

	/**
	 * Sets the x, y and z-coordinates of the bottom left corner
	 * 
	 * @param x
	 *            The x-coordinate
	 * @param y
	 *            The y-coordinate
	 * @param z
	 *            the z-coordinate
	 * @return this rectangle for chaining
	 */
	public Rectangle3D setPosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;

		return this;
	}

	/**
	 * Sets the width and height of this rectangle
	 * 
	 * @param width
	 *            The width
	 * @param height
	 *            The height
	 * @return this rectangle for chaining
	 */
	public Rectangle setSize(float width, float height) {
		this.width = width;
		this.height = height;

		return this;
	}

	/**
	 * Sets the squared size of this rectangle
	 * 
	 * @param sizeXY
	 *            The size
	 * @return this rectangle for chaining
	 */
	public Rectangle setSize(float sizeXY) {
		this.width = sizeXY;
		this.height = sizeXY;

		return this;
	}

	/**
	 * @return the Vector2 with size of this rectangle
	 * @param size
	 *            The Vector2
	 */
	public Vector2 getSize(Vector2 size) {
		return size.set(width, height);
	}

	/**
	 * @param x
	 *            point x coordinate
	 * @param y
	 *            point y coordinate
	 * @return whether the point is contained in the rectangle
	 */
	public boolean contains(float x, float y) {
		return this.x <= x && this.x + this.width >= x && this.y <= y
				&& this.y + this.height >= y;
	}

	/**
	 * @param point
	 *            The coordinates vector
	 * @return whether the point is contained in the rectangle
	 */
	public boolean contains(Vector2 point) {
		return contains(point.x, point.y);
	}

	/**
	 * @param rectangle
	 *            the other {@link Rectangle}.
	 * @return whether the other rectangle is contained in this rectangle.
	 */
	public boolean contains(Rectangle rectangle) {
		float xmin = rectangle.x;
		float xmax = xmin + rectangle.width;

		float ymin = rectangle.y;
		float ymax = ymin + rectangle.height;

		return ((xmin > x && xmin < x + width) && (xmax > x && xmax < x + width))
				&& ((ymin > y && ymin < y + height) && (ymax > y && ymax < y
						+ height));
	}

	/**
	 * @param r
	 *            the other {@link Rectangle}
	 * @return whether this rectangle overlaps the other rectangle.
	 */
	public boolean overlaps(Rectangle r) {
		return x < r.x + r.width && x + width > r.x && y < r.y + r.height
				&& y + height > r.y;
	}

	/**
	 * Sets the values of the given rectangle to this rectangle.
	 * 
	 * @param rect
	 *            the other rectangle
	 * @return this rectangle for chaining
	 */
	public Rectangle set(Rectangle rect) {
		this.x = rect.x;
		this.y = rect.y;
		this.width = rect.width;
		this.height = rect.height;

		return this;
	}

	/**
	 * Merges this rectangle with the other rectangle.
	 * 
	 * @param rect
	 *            the other rectangle
	 * @return this rectangle for chaining
	 */
	public Rectangle merge(Rectangle rect) {
		float minX = Math.min(x, rect.x);
		float maxX = Math.max(x + width, rect.x + rect.width);
		x = minX;
		width = maxX - minX;

		float minY = Math.min(y, rect.y);
		float maxY = Math.max(y + height, rect.y + rect.height);
		y = minY;
		height = maxY - minY;

		return this;
	}

	/**
	 * Calculates the aspect ratio ( width / height ) of this rectangle
	 * 
	 * @return the aspect ratio of this rectangle. Returns Float.NaN if height
	 *         is 0 to avoid ArithmeticException
	 */
	public float getAspectRatio() {
		return (height == 0) ? Float.NaN : width / height;
	}

	/**
	 * Calculates the center of the rectangle. Results are located in the given
	 * Vector2
	 * 
	 * @param vector
	 *            the Vector2 to use
	 * @return the given vector with results stored inside
	 */
	public Vector2 getCenter(Vector2 vector) {
		vector.x = x + width / 2;
		vector.y = y + height / 2;
		return vector;
	}

	/**
	 * Moves this rectangle so that its center point is located at a given
	 * position
	 * 
	 * @param x
	 *            the position's x
	 * @param y
	 *            the position's y
	 * @return this for chaining
	 */
	public Rectangle setCenter(float x, float y) {
		setPosition(x - width / 2, y - height / 2);
		return this;
	}

	/**
	 * Moves this rectangle so that its center point is located at a given
	 * position
	 * 
	 * @param position
	 *            the position
	 * @return this for chaining
	 */
	public Rectangle setCenter(Vector2 position) {
		setPosition(position.x - width / 2, position.y - height / 2);
		return this;
	}

	/**
	 * Fits this rectangle around another rectangle while maintaining aspect
	 * ratio This scales and centers the rectangle to the other rectangle (e.g.
	 * Having a camera translate and scale to show a given area)
	 * 
	 * @param rect
	 *            the other rectangle to fit this rectangle around
	 * @return this rectangle for chaining
	 */
	public Rectangle fitOutside(Rectangle rect) {
		float ratio = getAspectRatio();

		if (ratio > rect.getAspectRatio()) {
			// Wider than tall
			setSize(rect.height * ratio, rect.height);
		} else {
			// Taller than wide
			setSize(rect.width, rect.width / ratio);
		}

		setPosition((rect.x + rect.width / 2) - width / 2,
				(rect.y + rect.height / 2) - height / 2);
		return this;
	}

	/**
	 * Fits this rectangle into another rectangle while maintaining aspect
	 * ratio. This scales and centers the rectangle to the other rectangle (e.g.
	 * Scaling a texture within a arbitrary cell without squeezing)
	 * 
	 * @param rect
	 *            the other rectangle to fit this rectangle inside
	 * @return this rectangle for chaining
	 */
	public Rectangle fitInside(Rectangle rect) {
		float ratio = getAspectRatio();

		if (ratio < rect.getAspectRatio()) {
			// Taller than wide
			setSize(rect.height * ratio, rect.height);
		} else {
			// Wider than tall
			setSize(rect.width, rect.width / ratio);
		}

		setPosition((rect.x + rect.width / 2) - width / 2,
				(rect.y + rect.height / 2) - height / 2);
		return this;
	}

	public String toString() {
		return x + "," + y + "," + width + "," + height;
	}
}
