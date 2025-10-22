public class Vector2f {
    public float x;
    public float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2f add(Vector2f other) {
        return new Vector2f(this.x + other.x, this.y + other.y);
    }

    public Vector2f sub(Vector2f other) {
        return new Vector2f(this.x - other.x, this.y - other.y);
    }

    public Vector2f mul(float scalar) {
        return new Vector2f(this.x * scalar, this.y * scalar);
    }

    public float length() {
        return (float)Math.sqrt(x * x + y * y);
    }

    public Vector2f normalize() {
        float len = length();
        if (len != 0) {
            return new Vector2f(x / len, y / len);
        }
        return new Vector2f(0, 0);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
