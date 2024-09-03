public class Classroom {
    protected String name;
    protected String shape;
    protected double width;
    protected double length;
    protected double height;

    Classroom(Builder<?> builder) {
        this.name = builder.name;
        this.shape = builder.shape;
        this.width = builder.width;
        this.length = builder.length;
        this.height = builder.height;
    }


    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> {
        private String name;
        private String shape;
        private double width;
        private double length;
        private double height;


        protected T self() {
            return (T) this;
        }

        public T setName(String name) {
            this.name = name;
            return self();
        }

        public T setShape(String shape) {
            this.shape = shape;
            return self();
        }

        public T setWidth(double width) {
            this.width = width;
            return self();
        }

        public T setLength(double length) {
            this.length = length;
            return self();
        }

        public T setHeight(double height) {
            this.height = height;
            return self();
        }

        public Classroom build() {
            return new Classroom(this);
        }
    }

    public static Builder<?> builder() {
        return new Builder<>();
    }








    public String getName() {
        return name;
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getLength() {
        return length;
    }
    
    public double getHeight() {
        return height;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setWidth(double width) {
        this.width = width;
    }
    
    public void setLength(double length) {
        this.length = length;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }


    public String getShape() {
        return shape;
    }
}
