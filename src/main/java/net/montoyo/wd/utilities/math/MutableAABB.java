/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 * Refactored for 1.21.1: Changed from inheritance to composition
 * because AABB fields are final in 1.21.1
 */

package net.montoyo.wd.utilities.math;

import net.minecraft.world.phys.AABB;

public final class MutableAABB {
    // Mutable coordinate fields (composition instead of inheritance)
    private double minX, minY, minZ;
    private double maxX, maxY, maxZ;

    public MutableAABB() {
        this.minX = this.minY = this.minZ = 0;
        this.maxX = this.maxY = this.maxZ = 0;
    }

    public MutableAABB(Vector3i pos) {
        this.minX = this.maxX = pos.x;
        this.minY = this.maxY = pos.y;
        this.minZ = this.maxZ = pos.z;
    }

    public MutableAABB(Vector3i a, Vector3i b) {
        this.minX = a.x;
        this.minY = a.y;
        this.minZ = a.z;
        this.maxX = b.x;
        this.maxY = b.y;
        this.maxZ = b.z;
    }

    public MutableAABB(AABB bb) {
        this.minX = bb.minX;
        this.minY = bb.minY;
        this.minZ = bb.minZ;
        this.maxX = bb.maxX;
        this.maxY = bb.maxY;
        this.maxZ = bb.maxZ;
    }

    public MutableAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minX = x1;
        this.minY = y1;
        this.minZ = z1;
        this.maxX = x2;
        this.maxY = y2;
        this.maxZ = z2;
    }

    public MutableAABB expand(Vector3i vec) {
        if (vec.x > maxX)
            maxX = vec.x;
        else if (vec.x < minX)
            minX = vec.x;

        if (vec.y > maxY)
            maxY = vec.y;
        else if (vec.y < minY)
            minY = vec.y;

        if (vec.z > maxZ)
            maxZ = vec.z;
        else if (vec.z < minZ)
            minZ = vec.z;

        return this;
    }

    public MutableAABB move(double x, double y, double z) {
        minX += x;
        minY += y;
        minZ += z;
        maxX += x;
        maxY += y;
        maxZ += z;
        return this;
    }

    public AABB toMc() {
        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public void setAndCheck(double x1, double y1, double z1, double x2, double y2, double z2) {
        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);
        minZ = Math.min(z1, z2);

        maxX = Math.max(x1, x2);
        maxY = Math.max(y1, y2);
        maxZ = Math.max(z1, z2);
    }

    public void expand(double x1, double y1, double z1, double x2, double y2, double z2) {
        minX = Math.min(minX, Math.min(x1, x2));
        minY = Math.min(minY, Math.min(y1, y2));
        minZ = Math.min(minZ, Math.min(z1, z2));

        maxX = Math.max(maxX, Math.max(x1, x2));
        maxY = Math.max(maxY, Math.max(y1, y2));
        maxZ = Math.max(maxZ, Math.max(z1, z2));
    }

    // Getters for compatibility with code expecting AABB-like interface
    public double getMinX() { return minX; }
    public double getMinY() { return minY; }
    public double getMinZ() { return minZ; }
    public double getMaxX() { return maxX; }
    public double getMaxY() { return maxY; }
    public double getMaxZ() { return maxZ; }
}
