package com.example.arquitecturaandroid.DTO;

public class Estudiante {

    public class Application {
        private String name;
        private String special;
        private String id;
        private String city;
        private float phone;
        private String padres;


        // Getter Methods

        public String getName() {
            return name;
        }

        public String getSpecial() {
            return special;
        }

        public String getId() {
            return id;
        }

        public String getCity() {
            return city;
        }

        public float getPhone() {
            return phone;
        }

        public String getPadres() {
            return padres;
        }

        // Setter Methods

        public void setName( String name ) {
            this.name = name;
        }

        public void setSpecial( String special ) {
            this.special = special;
        }

        public void setId( String id ) {
            this.id = id;
        }

        public void setCity( String city ) {
            this.city = city;
        }

        public void setPhone( float phone ) {
            this.phone = phone;
        }

        public void setPadres( String padres ) {
            this.padres = padres;
        }
    }
}
