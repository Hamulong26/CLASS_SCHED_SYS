package model;

public record User(int id, String username, String password, String role, String status) {
    @Override
    public String toString() {
        return String.format("%-4d %-12s %-16s %-10s", id, username, role, status);
    }
}