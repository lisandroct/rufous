package org.rufousengine.math

inline val Vector4.xx: Vector2
    get() = Vector2(x, x)
inline var Vector4.xy: Vector2
    get() = Vector2(x, y)
    set(value) {
        x = value.x
        y = value.y
    }
inline var Vector4.xz: Vector2
    get() = Vector2(x, z)
    set(value) {
        x = value.x
        z = value.y
    }
inline var Vector4.xw: Vector2
    get() = Vector2(x, w)
    set(value) {
        x = value.x
        w = value.y
    }
inline var Vector4.yx: Vector2
    get() = Vector2(y, x)
    set(value) {
        y = value.x
        x = value.y
    }
inline val Vector4.yy: Vector2
    get() = Vector2(y, y)
inline var Vector4.yz: Vector2
    get() = Vector2(y, z)
    set(value) {
        y = value.x
        z = value.y
    }
inline var Vector4.yw: Vector2
    get() = Vector2(y, w)
    set(value) {
        y = value.x
        w = value.y
    }
inline var Vector4.zx: Vector2
    get() = Vector2(z, x)
    set(value) {
        z = value.x
        x = value.y
    }
inline var Vector4.zy: Vector2
    get() = Vector2(z, y)
    set(value) {
        z = value.x
        y = value.y
    }
inline val Vector4.zz: Vector2
    get() = Vector2(z, z)
inline var Vector4.zw: Vector2
    get() = Vector2(z, w)
    set(value) {
        z = value.x
        w = value.y
    }
inline var Vector4.wx: Vector2
    get() = Vector2(w, x)
    set(value) {
        w = value.x
        x = value.y
    }
inline var Vector4.wy: Vector2
    get() = Vector2(w, y)
    set(value) {
        w = value.x
        y = value.y
    }
inline var Vector4.wz: Vector2
    get() = Vector2(w, z)
    set(value) {
        w = value.x
        z = value.y
    }
inline val Vector4.ww: Vector2
    get() = Vector2(w, w)

inline val Vector4.xxx: Vector3
    get() = Vector3(x, x, x)
inline val Vector4.xxy: Vector3
    get() = Vector3(x, x, y)
inline val Vector4.xxz: Vector3
    get() = Vector3(x, x, z)
inline val Vector4.xxw: Vector3
    get() = Vector3(x, x, w)
inline val Vector4.xyx: Vector3
    get() = Vector3(x, y, x)
inline val Vector4.xyy: Vector3
    get() = Vector3(x, y, y)
inline var Vector4.xyz: Vector3
    get() = Vector3(x, y, z)
    set(value) {
        x = value.x
        y = value.y
        z = value.z
    }
inline var Vector4.xyw: Vector3
    get() = Vector3(x, y, w)
    set(value) {
        x = value.x
        y = value.y
        w = value.z
    }
inline val Vector4.xzx: Vector3
    get() = Vector3(x, z, x)
inline var Vector4.xzy: Vector3
    get() = Vector3(x, z, y)
    set(value) {
        x = value.x
        z = value.y
        y = value.z
    }
inline val Vector4.xzz: Vector3
    get() = Vector3(x, z, z)
inline var Vector4.xzw: Vector3
    get() = Vector3(x, z, w)
    set(value) {
        x = value.x
        z = value.y
        w = value.z
    }
inline val Vector4.xwx: Vector3
    get() = Vector3(x, w, x)
inline var Vector4.xwy: Vector3
    get() = Vector3(x, w, y)
    set(value) {
        x = value.x
        w = value.y
        y = value.z
    }
inline var Vector4.xwz: Vector3
    get() = Vector3(x, w, z)
    set(value) {
        x = value.x
        w = value.y
        z = value.z
    }
inline val Vector4.xww: Vector3
    get() = Vector3(x, w, w)
inline val Vector4.yxx: Vector3
    get() = Vector3(y, x, x)
inline val Vector4.yxy: Vector3
    get() = Vector3(y, x, y)
inline var Vector4.yxz: Vector3
    get() = Vector3(y, x, z)
    set(value) {
        y = value.x
        x = value.y
        z = value.z
    }
inline var Vector4.yxw: Vector3
    get() = Vector3(y, x, w)
    set(value) {
        y = value.x
        x = value.y
        w = value.z
    }
inline val Vector4.yyx: Vector3
    get() = Vector3(y, y, x)
inline val Vector4.yyy: Vector3
    get() = Vector3(y, y, y)
inline val Vector4.yyz: Vector3
    get() = Vector3(y, y, z)
inline val Vector4.yyw: Vector3
    get() = Vector3(y, y, w)
inline var Vector4.yzx: Vector3
    get() = Vector3(y, z, x)
    set(value) {
        y = value.x
        z = value.y
        x = value.z
    }
inline val Vector4.yzy: Vector3
    get() = Vector3(y, z, y)
inline val Vector4.yzz: Vector3
    get() = Vector3(y, z, z)
inline var Vector4.yzw: Vector3
    get() = Vector3(y, z, w)
    set(value) {
        y = value.x
        z = value.y
        w = value.z
    }
inline var Vector4.ywx: Vector3
    get() = Vector3(y, w, x)
    set(value) {
        y = value.x
        w = value.y
        x = value.z
    }
inline val Vector4.ywy: Vector3
    get() = Vector3(y, w, y)
inline var Vector4.ywz: Vector3
    get() = Vector3(y, w, z)
    set(value) {
        y = value.x
        w = value.y
        z = value.z
    }
inline val Vector4.yww: Vector3
    get() = Vector3(y, w, w)
inline val Vector4.zxx: Vector3
    get() = Vector3(z, x, x)
inline var Vector4.zxy: Vector3
    get() = Vector3(z, x, y)
    set(value) {
        z = value.x
        x = value.y
        y = value.z
    }
inline val Vector4.zxz: Vector3
    get() = Vector3(z, x, z)
inline var Vector4.zxw: Vector3
    get() = Vector3(z, x, w)
    set(value) {
        z = value.x
        x = value.y
        w = value.z
    }
inline var Vector4.zyx: Vector3
    get() = Vector3(z, y, x)
    set(value) {
        z = value.x
        y = value.y
        x = value.z
    }
inline val Vector4.zyy: Vector3
    get() = Vector3(z, y, y)
inline val Vector4.zyz: Vector3
    get() = Vector3(z, y, z)
inline var Vector4.zyw: Vector3
    get() = Vector3(z, y, w)
    set(value) {
        z = value.x
        y = value.y
        w = value.z
    }
inline val Vector4.zzx: Vector3
    get() = Vector3(z, z, x)
inline val Vector4.zzy: Vector3
    get() = Vector3(z, z, y)
inline val Vector4.zzz: Vector3
    get() = Vector3(z, z, z)
inline val Vector4.zzw: Vector3
    get() = Vector3(z, z, w)
inline var Vector4.zwx: Vector3
    get() = Vector3(z, w, x)
    set(value) {
        z = value.x
        w = value.y
        x = value.z
    }
inline var Vector4.zwy: Vector3
    get() = Vector3(z, w, y)
    set(value) {
        z = value.x
        w = value.y
        y = value.z
    }
inline val Vector4.zwz: Vector3
    get() = Vector3(z, w, z)
inline val Vector4.zww: Vector3
    get() = Vector3(z, w, w)
inline val Vector4.wxx: Vector3
    get() = Vector3(w, x, x)
inline var Vector4.wxy: Vector3
    get() = Vector3(w, x, y)
    set(value) {
        w = value.x
        x = value.y
        y = value.z
    }
inline var Vector4.wxz: Vector3
    get() = Vector3(w, x, z)
    set(value) {
        w = value.x
        x = value.y
        z = value.z
    }
inline val Vector4.wxw: Vector3
    get() = Vector3(w, x, w)
inline var Vector4.wyx: Vector3
    get() = Vector3(w, y, x)
    set(value) {
        w = value.x
        y = value.y
        x = value.z
    }
inline val Vector4.wyy: Vector3
    get() = Vector3(w, y, y)
inline var Vector4.wyz: Vector3
    get() = Vector3(w, y, z)
    set(value) {
        w = value.x
        y = value.y
        z = value.z
    }
inline val Vector4.wyw: Vector3
    get() = Vector3(w, y, w)
inline var Vector4.wzx: Vector3
    get() = Vector3(w, z, x)
    set(value) {
        w = value.x
        z = value.y
        x = value.z
    }
inline var Vector4.wzy: Vector3
    get() = Vector3(w, z, y)
    set(value) {
        w = value.x
        z = value.y
        y = value.z
    }
inline val Vector4.wzz: Vector3
    get() = Vector3(w, z, z)
inline val Vector4.wzw: Vector3
    get() = Vector3(w, z, w)
inline val Vector4.wwx: Vector3
    get() = Vector3(w, w, x)
inline val Vector4.wwy: Vector3
    get() = Vector3(w, w, y)
inline val Vector4.wwz: Vector3
    get() = Vector3(w, w, z)
inline val Vector4.www: Vector3
    get() = Vector3(w, w, w)

inline val Vector4.xxxx: Vector4
    get() = Vector4(x, x, x, x)
inline val Vector4.xxxy: Vector4
    get() = Vector4(x, x, x, y)
inline val Vector4.xxxz: Vector4
    get() = Vector4(x, x, x, z)
inline val Vector4.xxxw: Vector4
    get() = Vector4(x, x, x, w)
inline val Vector4.xxyx: Vector4
    get() = Vector4(x, x, y, x)
inline val Vector4.xxyy: Vector4
    get() = Vector4(x, x, y, y)
inline val Vector4.xxyz: Vector4
    get() = Vector4(x, x, y, z)
inline val Vector4.xxyw: Vector4
    get() = Vector4(x, x, y, w)
inline val Vector4.xxzx: Vector4
    get() = Vector4(x, x, z, x)
inline val Vector4.xxzy: Vector4
    get() = Vector4(x, x, z, y)
inline val Vector4.xxzz: Vector4
    get() = Vector4(x, x, z, z)
inline val Vector4.xxzw: Vector4
    get() = Vector4(x, x, z, w)
inline val Vector4.xxwx: Vector4
    get() = Vector4(x, x, w, x)
inline val Vector4.xxwy: Vector4
    get() = Vector4(x, x, w, y)
inline val Vector4.xxwz: Vector4
    get() = Vector4(x, x, w, z)
inline val Vector4.xxww: Vector4
    get() = Vector4(x, x, w, w)
inline val Vector4.xyxx: Vector4
    get() = Vector4(x, y, x, x)
inline val Vector4.xyxy: Vector4
    get() = Vector4(x, y, x, y)
inline val Vector4.xyxz: Vector4
    get() = Vector4(x, y, x, z)
inline val Vector4.xyxw: Vector4
    get() = Vector4(x, y, x, w)
inline val Vector4.xyyx: Vector4
    get() = Vector4(x, y, y, x)
inline val Vector4.xyyy: Vector4
    get() = Vector4(x, y, y, y)
inline val Vector4.xyyz: Vector4
    get() = Vector4(x, y, y, z)
inline val Vector4.xyyw: Vector4
    get() = Vector4(x, y, y, w)
inline val Vector4.xyzx: Vector4
    get() = Vector4(x, y, z, x)
inline val Vector4.xyzy: Vector4
    get() = Vector4(x, y, z, y)
inline val Vector4.xyzz: Vector4
    get() = Vector4(x, y, z, z)
inline var Vector4.xyzw: Vector4
    get() = Vector4(x, y, z, w)
    set(value) {
        x = value.x
        y = value.y
        z = value.z
        w = value.w
    }
inline val Vector4.xywx: Vector4
    get() = Vector4(x, y, w, x)
inline val Vector4.xywy: Vector4
    get() = Vector4(x, y, w, y)
inline var Vector4.xywz: Vector4
    get() = Vector4(x, y, w, z)
    set(value) {
        x = value.x
        y = value.y
        w = value.z
        z = value.w
    }
inline val Vector4.xyww: Vector4
    get() = Vector4(x, y, w, w)
inline val Vector4.xzxx: Vector4
    get() = Vector4(x, z, x, x)
inline val Vector4.xzxy: Vector4
    get() = Vector4(x, z, x, y)
inline val Vector4.xzxz: Vector4
    get() = Vector4(x, z, x, z)
inline val Vector4.xzxw: Vector4
    get() = Vector4(x, z, x, w)
inline val Vector4.xzyx: Vector4
    get() = Vector4(x, z, y, x)
inline val Vector4.xzyy: Vector4
    get() = Vector4(x, z, y, y)
inline val Vector4.xzyz: Vector4
    get() = Vector4(x, z, y, z)
inline var Vector4.xzyw: Vector4
    get() = Vector4(x, z, y, w)
    set(value) {
        x = value.x
        z = value.y
        y = value.z
        w = value.w
    }
inline val Vector4.xzzx: Vector4
    get() = Vector4(x, z, z, x)
inline val Vector4.xzzy: Vector4
    get() = Vector4(x, z, z, y)
inline val Vector4.xzzz: Vector4
    get() = Vector4(x, z, z, z)
inline val Vector4.xzzw: Vector4
    get() = Vector4(x, z, z, w)
inline val Vector4.xzwx: Vector4
    get() = Vector4(x, z, w, x)
inline var Vector4.xzwy: Vector4
    get() = Vector4(x, z, w, y)
    set(value) {
        x = value.x
        z = value.y
        w = value.z
        y = value.w
    }
inline val Vector4.xzwz: Vector4
    get() = Vector4(x, z, w, z)
inline val Vector4.xzww: Vector4
    get() = Vector4(x, z, w, w)
inline val Vector4.xwxx: Vector4
    get() = Vector4(x, w, x, x)
inline val Vector4.xwxy: Vector4
    get() = Vector4(x, w, x, y)
inline val Vector4.xwxz: Vector4
    get() = Vector4(x, w, x, z)
inline val Vector4.xwxw: Vector4
    get() = Vector4(x, w, x, w)
inline val Vector4.xwyx: Vector4
    get() = Vector4(x, w, y, x)
inline val Vector4.xwyy: Vector4
    get() = Vector4(x, w, y, y)
inline var Vector4.xwyz: Vector4
    get() = Vector4(x, w, y, z)
    set(value) {
        x = value.x
        w = value.y
        y = value.z
        z = value.w
    }
inline val Vector4.xwyw: Vector4
    get() = Vector4(x, w, y, w)
inline val Vector4.xwzx: Vector4
    get() = Vector4(x, w, z, x)
inline var Vector4.xwzy: Vector4
    get() = Vector4(x, w, z, y)
    set(value) {
        x = value.x
        w = value.y
        z = value.z
        y = value.w
    }
inline val Vector4.xwzz: Vector4
    get() = Vector4(x, w, z, z)
inline val Vector4.xwzw: Vector4
    get() = Vector4(x, w, z, w)
inline val Vector4.xwwx: Vector4
    get() = Vector4(x, w, w, x)
inline val Vector4.xwwy: Vector4
    get() = Vector4(x, w, w, y)
inline val Vector4.xwwz: Vector4
    get() = Vector4(x, w, w, z)
inline val Vector4.xwww: Vector4
    get() = Vector4(x, w, w, w)
inline val Vector4.yxxx: Vector4
    get() = Vector4(y, x, x, x)
inline val Vector4.yxxy: Vector4
    get() = Vector4(y, x, x, y)
inline val Vector4.yxxz: Vector4
    get() = Vector4(y, x, x, z)
inline val Vector4.yxxw: Vector4
    get() = Vector4(y, x, x, w)
inline val Vector4.yxyx: Vector4
    get() = Vector4(y, x, y, x)
inline val Vector4.yxyy: Vector4
    get() = Vector4(y, x, y, y)
inline val Vector4.yxyz: Vector4
    get() = Vector4(y, x, y, z)
inline val Vector4.yxyw: Vector4
    get() = Vector4(y, x, y, w)
inline val Vector4.yxzx: Vector4
    get() = Vector4(y, x, z, x)
inline val Vector4.yxzy: Vector4
    get() = Vector4(y, x, z, y)
inline val Vector4.yxzz: Vector4
    get() = Vector4(y, x, z, z)
inline var Vector4.yxzw: Vector4
    get() = Vector4(y, x, z, w)
    set(value) {
        y = value.x
        x = value.y
        z = value.z
        w = value.w
    }
inline val Vector4.yxwx: Vector4
    get() = Vector4(y, x, w, x)
inline val Vector4.yxwy: Vector4
    get() = Vector4(y, x, w, y)
inline var Vector4.yxwz: Vector4
    get() = Vector4(y, x, w, z)
    set(value) {
        y = value.x
        x = value.y
        w = value.z
        z = value.w
    }
inline val Vector4.yxww: Vector4
    get() = Vector4(y, x, w, w)
inline val Vector4.yyxx: Vector4
    get() = Vector4(y, y, x, x)
inline val Vector4.yyxy: Vector4
    get() = Vector4(y, y, x, y)
inline val Vector4.yyxz: Vector4
    get() = Vector4(y, y, x, z)
inline val Vector4.yyxw: Vector4
    get() = Vector4(y, y, x, w)
inline val Vector4.yyyx: Vector4
    get() = Vector4(y, y, y, x)
inline val Vector4.yyyy: Vector4
    get() = Vector4(y, y, y, y)
inline val Vector4.yyyz: Vector4
    get() = Vector4(y, y, y, z)
inline val Vector4.yyyw: Vector4
    get() = Vector4(y, y, y, w)
inline val Vector4.yyzx: Vector4
    get() = Vector4(y, y, z, x)
inline val Vector4.yyzy: Vector4
    get() = Vector4(y, y, z, y)
inline val Vector4.yyzz: Vector4
    get() = Vector4(y, y, z, z)
inline val Vector4.yyzw: Vector4
    get() = Vector4(y, y, z, w)
inline val Vector4.yywx: Vector4
    get() = Vector4(y, y, w, x)
inline val Vector4.yywy: Vector4
    get() = Vector4(y, y, w, y)
inline val Vector4.yywz: Vector4
    get() = Vector4(y, y, w, z)
inline val Vector4.yyww: Vector4
    get() = Vector4(y, y, w, w)
inline val Vector4.yzxx: Vector4
    get() = Vector4(y, z, x, x)
inline val Vector4.yzxy: Vector4
    get() = Vector4(y, z, x, y)
inline val Vector4.yzxz: Vector4
    get() = Vector4(y, z, x, z)
inline var Vector4.yzxw: Vector4
    get() = Vector4(y, z, x, w)
    set(value) {
        y = value.x
        z = value.y
        x = value.z
        w = value.w
    }
inline val Vector4.yzyx: Vector4
    get() = Vector4(y, z, y, x)
inline val Vector4.yzyy: Vector4
    get() = Vector4(y, z, y, y)
inline val Vector4.yzyz: Vector4
    get() = Vector4(y, z, y, z)
inline val Vector4.yzyw: Vector4
    get() = Vector4(y, z, y, w)
inline val Vector4.yzzx: Vector4
    get() = Vector4(y, z, z, x)
inline val Vector4.yzzy: Vector4
    get() = Vector4(y, z, z, y)
inline val Vector4.yzzz: Vector4
    get() = Vector4(y, z, z, z)
inline val Vector4.yzzw: Vector4
    get() = Vector4(y, z, z, w)
inline var Vector4.yzwx: Vector4
    get() = Vector4(y, z, w, x)
    set(value) {
        y = value.x
        z = value.y
        w = value.z
        x = value.w
    }
inline val Vector4.yzwy: Vector4
    get() = Vector4(y, z, w, y)
inline val Vector4.yzwz: Vector4
    get() = Vector4(y, z, w, z)
inline val Vector4.yzww: Vector4
    get() = Vector4(y, z, w, w)
inline val Vector4.ywxx: Vector4
    get() = Vector4(y, w, x, x)
inline val Vector4.ywxy: Vector4
    get() = Vector4(y, w, x, y)
inline var Vector4.ywxz: Vector4
    get() = Vector4(y, w, x, z)
    set(value) {
        y = value.x
        w = value.y
        x = value.z
        z = value.w
    }
inline val Vector4.ywxw: Vector4
    get() = Vector4(y, w, x, w)
inline val Vector4.ywyx: Vector4
    get() = Vector4(y, w, y, x)
inline val Vector4.ywyy: Vector4
    get() = Vector4(y, w, y, y)
inline val Vector4.ywyz: Vector4
    get() = Vector4(y, w, y, z)
inline val Vector4.ywyw: Vector4
    get() = Vector4(y, w, y, w)
inline var Vector4.ywzx: Vector4
    get() = Vector4(y, w, z, x)
    set(value) {
        y = value.x
        w = value.y
        z = value.z
        x = value.w
    }
inline val Vector4.ywzy: Vector4
    get() = Vector4(y, w, z, y)
inline val Vector4.ywzz: Vector4
    get() = Vector4(y, w, z, z)
inline val Vector4.ywzw: Vector4
    get() = Vector4(y, w, z, w)
inline val Vector4.ywwx: Vector4
    get() = Vector4(y, w, w, x)
inline val Vector4.ywwy: Vector4
    get() = Vector4(y, w, w, y)
inline val Vector4.ywwz: Vector4
    get() = Vector4(y, w, w, z)
inline val Vector4.ywww: Vector4
    get() = Vector4(y, w, w, w)
inline val Vector4.zxxx: Vector4
    get() = Vector4(z, x, x, x)
inline val Vector4.zxxy: Vector4
    get() = Vector4(z, x, x, y)
inline val Vector4.zxxz: Vector4
    get() = Vector4(z, x, x, z)
inline val Vector4.zxxw: Vector4
    get() = Vector4(z, x, x, w)
inline val Vector4.zxyx: Vector4
    get() = Vector4(z, x, y, x)
inline val Vector4.zxyy: Vector4
    get() = Vector4(z, x, y, y)
inline val Vector4.zxyz: Vector4
    get() = Vector4(z, x, y, z)
inline var Vector4.zxyw: Vector4
    get() = Vector4(z, x, y, w)
    set(value) {
        z = value.x
        x = value.y
        y = value.z
        w = value.w
    }
inline val Vector4.zxzx: Vector4
    get() = Vector4(z, x, z, x)
inline val Vector4.zxzy: Vector4
    get() = Vector4(z, x, z, y)
inline val Vector4.zxzz: Vector4
    get() = Vector4(z, x, z, z)
inline val Vector4.zxzw: Vector4
    get() = Vector4(z, x, z, w)
inline val Vector4.zxwx: Vector4
    get() = Vector4(z, x, w, x)
inline var Vector4.zxwy: Vector4
    get() = Vector4(z, x, w, y)
    set(value) {
        z = value.x
        x = value.y
        w = value.z
        y = value.w
    }
inline val Vector4.zxwz: Vector4
    get() = Vector4(z, x, w, z)
inline val Vector4.zxww: Vector4
    get() = Vector4(z, x, w, w)
inline val Vector4.zyxx: Vector4
    get() = Vector4(z, y, x, x)
inline val Vector4.zyxy: Vector4
    get() = Vector4(z, y, x, y)
inline val Vector4.zyxz: Vector4
    get() = Vector4(z, y, x, z)
inline var Vector4.zyxw: Vector4
    get() = Vector4(z, y, x, w)
    set(value) {
        z = value.x
        y = value.y
        x = value.z
        w = value.w
    }
inline val Vector4.zyyx: Vector4
    get() = Vector4(z, y, y, x)
inline val Vector4.zyyy: Vector4
    get() = Vector4(z, y, y, y)
inline val Vector4.zyyz: Vector4
    get() = Vector4(z, y, y, z)
inline val Vector4.zyyw: Vector4
    get() = Vector4(z, y, y, w)
inline val Vector4.zyzx: Vector4
    get() = Vector4(z, y, z, x)
inline val Vector4.zyzy: Vector4
    get() = Vector4(z, y, z, y)
inline val Vector4.zyzz: Vector4
    get() = Vector4(z, y, z, z)
inline val Vector4.zyzw: Vector4
    get() = Vector4(z, y, z, w)
inline var Vector4.zywx: Vector4
    get() = Vector4(z, y, w, x)
    set(value) {
        z = value.x
        y = value.y
        w = value.z
        x = value.w
    }
inline val Vector4.zywy: Vector4
    get() = Vector4(z, y, w, y)
inline val Vector4.zywz: Vector4
    get() = Vector4(z, y, w, z)
inline val Vector4.zyww: Vector4
    get() = Vector4(z, y, w, w)
inline val Vector4.zzxx: Vector4
    get() = Vector4(z, z, x, x)
inline val Vector4.zzxy: Vector4
    get() = Vector4(z, z, x, y)
inline val Vector4.zzxz: Vector4
    get() = Vector4(z, z, x, z)
inline val Vector4.zzxw: Vector4
    get() = Vector4(z, z, x, w)
inline val Vector4.zzyx: Vector4
    get() = Vector4(z, z, y, x)
inline val Vector4.zzyy: Vector4
    get() = Vector4(z, z, y, y)
inline val Vector4.zzyz: Vector4
    get() = Vector4(z, z, y, z)
inline val Vector4.zzyw: Vector4
    get() = Vector4(z, z, y, w)
inline val Vector4.zzzx: Vector4
    get() = Vector4(z, z, z, x)
inline val Vector4.zzzy: Vector4
    get() = Vector4(z, z, z, y)
inline val Vector4.zzzz: Vector4
    get() = Vector4(z, z, z, z)
inline val Vector4.zzzw: Vector4
    get() = Vector4(z, z, z, w)
inline val Vector4.zzwx: Vector4
    get() = Vector4(z, z, w, x)
inline val Vector4.zzwy: Vector4
    get() = Vector4(z, z, w, y)
inline val Vector4.zzwz: Vector4
    get() = Vector4(z, z, w, z)
inline val Vector4.zzww: Vector4
    get() = Vector4(z, z, w, w)
inline val Vector4.zwxx: Vector4
    get() = Vector4(z, w, x, x)
inline var Vector4.zwxy: Vector4
    get() = Vector4(z, w, x, y)
    set(value) {
        z = value.x
        w = value.y
        x = value.z
        y = value.w
    }
inline val Vector4.zwxz: Vector4
    get() = Vector4(z, w, x, z)
inline val Vector4.zwxw: Vector4
    get() = Vector4(z, w, x, w)
inline var Vector4.zwyx: Vector4
    get() = Vector4(z, w, y, x)
    set(value) {
        z = value.x
        w = value.y
        y = value.z
        x = value.w
    }
inline val Vector4.zwyy: Vector4
    get() = Vector4(z, w, y, y)
inline val Vector4.zwyz: Vector4
    get() = Vector4(z, w, y, z)
inline val Vector4.zwyw: Vector4
    get() = Vector4(z, w, y, w)
inline val Vector4.zwzx: Vector4
    get() = Vector4(z, w, z, x)
inline val Vector4.zwzy: Vector4
    get() = Vector4(z, w, z, y)
inline val Vector4.zwzz: Vector4
    get() = Vector4(z, w, z, z)
inline val Vector4.zwzw: Vector4
    get() = Vector4(z, w, z, w)
inline val Vector4.zwwx: Vector4
    get() = Vector4(z, w, w, x)
inline val Vector4.zwwy: Vector4
    get() = Vector4(z, w, w, y)
inline val Vector4.zwwz: Vector4
    get() = Vector4(z, w, w, z)
inline val Vector4.zwww: Vector4
    get() = Vector4(z, w, w, w)
inline val Vector4.wxxx: Vector4
    get() = Vector4(w, x, x, x)
inline val Vector4.wxxy: Vector4
    get() = Vector4(w, x, x, y)
inline val Vector4.wxxz: Vector4
    get() = Vector4(w, x, x, z)
inline val Vector4.wxxw: Vector4
    get() = Vector4(w, x, x, w)
inline val Vector4.wxyx: Vector4
    get() = Vector4(w, x, y, x)
inline val Vector4.wxyy: Vector4
    get() = Vector4(w, x, y, y)
inline var Vector4.wxyz: Vector4
    get() = Vector4(w, x, y, z)
    set(value) {
        w = value.x
        x = value.y
        y = value.z
        z = value.w
    }
inline val Vector4.wxyw: Vector4
    get() = Vector4(w, x, y, w)
inline val Vector4.wxzx: Vector4
    get() = Vector4(w, x, z, x)
inline var Vector4.wxzy: Vector4
    get() = Vector4(w, x, z, y)
    set(value) {
        w = value.x
        x = value.y
        z = value.z
        y = value.w
    }
inline val Vector4.wxzz: Vector4
    get() = Vector4(w, x, z, z)
inline val Vector4.wxzw: Vector4
    get() = Vector4(w, x, z, w)
inline val Vector4.wxwx: Vector4
    get() = Vector4(w, x, w, x)
inline val Vector4.wxwy: Vector4
    get() = Vector4(w, x, w, y)
inline val Vector4.wxwz: Vector4
    get() = Vector4(w, x, w, z)
inline val Vector4.wxww: Vector4
    get() = Vector4(w, x, w, w)
inline val Vector4.wyxx: Vector4
    get() = Vector4(w, y, x, x)
inline val Vector4.wyxy: Vector4
    get() = Vector4(w, y, x, y)
inline var Vector4.wyxz: Vector4
    get() = Vector4(w, y, x, z)
    set(value) {
        w = value.x
        y = value.y
        x = value.z
        z = value.w
    }
inline val Vector4.wyxw: Vector4
    get() = Vector4(w, y, x, w)
inline val Vector4.wyyx: Vector4
    get() = Vector4(w, y, y, x)
inline val Vector4.wyyy: Vector4
    get() = Vector4(w, y, y, y)
inline val Vector4.wyyz: Vector4
    get() = Vector4(w, y, y, z)
inline val Vector4.wyyw: Vector4
    get() = Vector4(w, y, y, w)
inline var Vector4.wyzx: Vector4
    get() = Vector4(w, y, z, x)
    set(value) {
        w = value.x
        y = value.y
        z = value.z
        x = value.w
    }
inline val Vector4.wyzy: Vector4
    get() = Vector4(w, y, z, y)
inline val Vector4.wyzz: Vector4
    get() = Vector4(w, y, z, z)
inline val Vector4.wyzw: Vector4
    get() = Vector4(w, y, z, w)
inline val Vector4.wywx: Vector4
    get() = Vector4(w, y, w, x)
inline val Vector4.wywy: Vector4
    get() = Vector4(w, y, w, y)
inline val Vector4.wywz: Vector4
    get() = Vector4(w, y, w, z)
inline val Vector4.wyww: Vector4
    get() = Vector4(w, y, w, w)
inline val Vector4.wzxx: Vector4
    get() = Vector4(w, z, x, x)
inline var Vector4.wzxy: Vector4
    get() = Vector4(w, z, x, y)
    set(value) {
        w = value.x
        z = value.y
        x = value.z
        y = value.w
    }
inline val Vector4.wzxz: Vector4
    get() = Vector4(w, z, x, z)
inline val Vector4.wzxw: Vector4
    get() = Vector4(w, z, x, w)
inline var Vector4.wzyx: Vector4
    get() = Vector4(w, z, y, x)
    set(value) {
        w = value.x
        z = value.y
        y = value.z
        x = value.w
    }
inline val Vector4.wzyy: Vector4
    get() = Vector4(w, z, y, y)
inline val Vector4.wzyz: Vector4
    get() = Vector4(w, z, y, z)
inline val Vector4.wzyw: Vector4
    get() = Vector4(w, z, y, w)
inline val Vector4.wzzx: Vector4
    get() = Vector4(w, z, z, x)
inline val Vector4.wzzy: Vector4
    get() = Vector4(w, z, z, y)
inline val Vector4.wzzz: Vector4
    get() = Vector4(w, z, z, z)
inline val Vector4.wzzw: Vector4
    get() = Vector4(w, z, z, w)
inline val Vector4.wzwx: Vector4
    get() = Vector4(w, z, w, x)
inline val Vector4.wzwy: Vector4
    get() = Vector4(w, z, w, y)
inline val Vector4.wzwz: Vector4
    get() = Vector4(w, z, w, z)
inline val Vector4.wzww: Vector4
    get() = Vector4(w, z, w, w)
inline val Vector4.wwxx: Vector4
    get() = Vector4(w, w, x, x)
inline val Vector4.wwxy: Vector4
    get() = Vector4(w, w, x, y)
inline val Vector4.wwxz: Vector4
    get() = Vector4(w, w, x, z)
inline val Vector4.wwxw: Vector4
    get() = Vector4(w, w, x, w)
inline val Vector4.wwyx: Vector4
    get() = Vector4(w, w, y, x)
inline val Vector4.wwyy: Vector4
    get() = Vector4(w, w, y, y)
inline val Vector4.wwyz: Vector4
    get() = Vector4(w, w, y, z)
inline val Vector4.wwyw: Vector4
    get() = Vector4(w, w, y, w)
inline val Vector4.wwzx: Vector4
    get() = Vector4(w, w, z, x)
inline val Vector4.wwzy: Vector4
    get() = Vector4(w, w, z, y)
inline val Vector4.wwzz: Vector4
    get() = Vector4(w, w, z, z)
inline val Vector4.wwzw: Vector4
    get() = Vector4(w, w, z, w)
inline val Vector4.wwwx: Vector4
    get() = Vector4(w, w, w, x)
inline val Vector4.wwwy: Vector4
    get() = Vector4(w, w, w, y)
inline val Vector4.wwwz: Vector4
    get() = Vector4(w, w, w, z)
inline val Vector4.wwww: Vector4
    get() = Vector4(w, w, w, w)