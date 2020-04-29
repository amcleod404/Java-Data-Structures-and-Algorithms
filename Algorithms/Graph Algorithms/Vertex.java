/**
 * Class representing a vertex.
 *
 */
public class Vertex<T> {

    private T data;

    /**
     * Creates a Vertex object holding the given data.
     *
     * @param data the object that is stored in this Vertex
     * @throws IllegalArgumentException if data is null
     */
    public Vertex(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        this.data = data;
    }

    /**
     * Gets the data.
     *
     * @return the data of this vertex
     */
    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Vertex) {
            return data.equals(((Vertex<?>) o).data);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}