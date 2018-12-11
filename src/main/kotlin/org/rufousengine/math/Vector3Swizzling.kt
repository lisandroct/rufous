package org.rufousengine.math

inline val Vector3.xx: Vector2
    get() = Vector2(x, x)
inline var Vector3.xy: Vector2
    get() = Vector2(x, y)
    set(value) {
        x = value.x
        y = value.y
    }
inline var Vector3.xz: Vector2
    get() = Vector2(x, z)
    set(value) {
        x = value.x
        z = value.y
    }
inline val Vector3.xw: Vector2
    get() = Vector2(x, w)
inline var Vector3.yx: Vector2
    get() = Vector2(y, x)
    set(value) {
        y = value.x
        x = value.y
    }
inline val Vector3.yy: Vector2
    get() = Vector2(y, y)
inline var Vector3.yz: Vector2
    get() = Vector2(y, z)
    set(value) {
        y = value.x
        z = value.y
    }
inline val Vector3.yw: Vector2
    get() = Vector2(y, w)
inline var Vector3.zx: Vector2
    get() = Vector2(z, x)
    set(value) {
        z = value.x
        x = value.y
    }
inline var Vector3.zy: Vector2
    get() = Vector2(z, y)
    set(value) {
        z = value.x
        y = value.y
    }
inline val Vector3.zz: Vector2
    get() = Vector2(z, z)
inline val Vector3.zw: Vector2
    get() = Vector2(z, w)
inline val Vector3.wx: Vector2
    get() = Vector2(w, x)
inline val Vector3.wy: Vector2
    get() = Vector2(w, y)
inline val Vector3.wz: Vector2
    get() = Vector2(w, z)
inline val Vector3.ww: Vector2
    get() = Vector2(w, w)

inline val Vector3.xxx: Vector3
    get() = Vector3(x, x, x)
inline val Vector3.xxy: Vector3
    get() = Vector3(x, x, y)
inline val Vector3.xxz: Vector3
    get() = Vector3(x, x, z)
inline val Vector3.xxw: Vector3
    get() = Vector3(x, x, w)
inline val Vector3.xyx: Vector3
    get() = Vector3(x, y, x)
inline val Vector3.xyy: Vector3
    get() = Vector3(x, y, y)
inline var Vector3.xyz: Vector3
    get() = Vector3(x, y, z)
    set(value) {
        x = value.x
        y = value.y
        z = value.z
    }
inline val Vector3.xyw: Vector3
    get() = Vector3(x, y, w)
inline val Vector3.xzx: Vector3
    get() = Vector3(x, z, x)
inline var Vector3.xzy: Vector3
    get() = Vector3(x, z, y)
    set(value) {
        x = value.x
        z = value.y
        y = value.z
    }
inline val Vector3.xzz: Vector3
    get() = Vector3(x, z, z)
inline val Vector3.xzw: Vector3
    get() = Vector3(x, z, w)
inline val Vector3.xwx: Vector3
    get() = Vector3(x, w, x)
inline val Vector3.xwy: Vector3
    get() = Vector3(x, w, y)
inline val Vector3.xwz: Vector3
    get() = Vector3(x, w, z)
inline val Vector3.xww: Vector3
    get() = Vector3(x, w, w)
inline val Vector3.yxx: Vector3
    get() = Vector3(y, x, x)
inline val Vector3.yxy: Vector3
    get() = Vector3(y, x, y)
inline var Vector3.yxz: Vector3
    get() = Vector3(y, x, z)
    set(value) {
        y = value.x
        x = value.y
        z = value.z
    }
inline val Vector3.yxw: Vector3
    get() = Vector3(y, x, w)
inline val Vector3.yyx: Vector3
    get() = Vector3(y, y, x)
inline val Vector3.yyy: Vector3
    get() = Vector3(y, y, y)
inline val Vector3.yyz: Vector3
    get() = Vector3(y, y, z)
inline val Vector3.yyw: Vector3
    get() = Vector3(y, y, w)
inline var Vector3.yzx: Vector3
    get() = Vector3(y, z, x)
    set(value) {
        y = value.x
        z = value.y
        x = value.z
    }
inline val Vector3.yzy: Vector3
    get() = Vector3(y, z, y)
inline val Vector3.yzz: Vector3
    get() = Vector3(y, z, z)
inline val Vector3.yzw: Vector3
    get() = Vector3(y, z, w)
inline val Vector3.ywx: Vector3
    get() = Vector3(y, w, x)
inline val Vector3.ywy: Vector3
    get() = Vector3(y, w, y)
inline val Vector3.ywz: Vector3
    get() = Vector3(y, w, z)
inline val Vector3.yww: Vector3
    get() = Vector3(y, w, w)
inline val Vector3.zxx: Vector3
    get() = Vector3(z, x, x)
inline var Vector3.zxy: Vector3
    get() = Vector3(z, x, y)
    set(value) {
        z = value.x
        x = value.y
        y = value.z
    }
inline val Vector3.zxz: Vector3
    get() = Vector3(z, x, z)
inline val Vector3.zxw: Vector3
    get() = Vector3(z, x, w)
inline var Vector3.zyx: Vector3
    get() = Vector3(z, y, x)
    set(value) {
        z = value.x
        y = value.y
        x = value.z
    }
inline val Vector3.zyy: Vector3
    get() = Vector3(z, y, y)
inline val Vector3.zyz: Vector3
    get() = Vector3(z, y, z)
inline val Vector3.zyw: Vector3
    get() = Vector3(z, y, w)
inline val Vector3.zzx: Vector3
    get() = Vector3(z, z, x)
inline val Vector3.zzy: Vector3
    get() = Vector3(z, z, y)
inline val Vector3.zzz: Vector3
    get() = Vector3(z, z, z)
inline val Vector3.zzw: Vector3
    get() = Vector3(z, z, w)
inline val Vector3.zwx: Vector3
    get() = Vector3(z, w, x)
inline val Vector3.zwy: Vector3
    get() = Vector3(z, w, y)
inline val Vector3.zwz: Vector3
    get() = Vector3(z, w, z)
inline val Vector3.zww: Vector3
    get() = Vector3(z, w, w)
inline val Vector3.wxx: Vector3
    get() = Vector3(w, x, x)
inline val Vector3.wxy: Vector3
    get() = Vector3(w, x, y)
inline val Vector3.wxz: Vector3
    get() = Vector3(w, x, z)
inline val Vector3.wxw: Vector3
    get() = Vector3(w, x, w)
inline val Vector3.wyx: Vector3
    get() = Vector3(w, y, x)
inline val Vector3.wyy: Vector3
    get() = Vector3(w, y, y)
inline val Vector3.wyz: Vector3
    get() = Vector3(w, y, z)
inline val Vector3.wyw: Vector3
    get() = Vector3(w, y, w)
inline val Vector3.wzx: Vector3
    get() = Vector3(w, z, x)
inline val Vector3.wzy: Vector3
    get() = Vector3(w, z, y)
inline val Vector3.wzz: Vector3
    get() = Vector3(w, z, z)
inline val Vector3.wzw: Vector3
    get() = Vector3(w, z, w)
inline val Vector3.wwx: Vector3
    get() = Vector3(w, w, x)
inline val Vector3.wwy: Vector3
    get() = Vector3(w, w, y)
inline val Vector3.wwz: Vector3
    get() = Vector3(w, w, z)
inline val Vector3.www: Vector3
    get() = Vector3(w, w, w)

inline val Vector3.xxxx: Vector4
    get() = Vector4(x, x, x, x)
inline val Vector3.xxxy: Vector4
    get() = Vector4(x, x, x, y)
inline val Vector3.xxxz: Vector4
    get() = Vector4(x, x, x, z)
inline val Vector3.xxxw: Vector4
    get() = Vector4(x, x, x, w)
inline val Vector3.xxyx: Vector4
    get() = Vector4(x, x, y, x)
inline val Vector3.xxyy: Vector4
    get() = Vector4(x, x, y, y)
inline val Vector3.xxyz: Vector4
    get() = Vector4(x, x, y, z)
inline val Vector3.xxyw: Vector4
    get() = Vector4(x, x, y, w)
inline val Vector3.xxzx: Vector4
    get() = Vector4(x, x, z, x)
inline val Vector3.xxzy: Vector4
    get() = Vector4(x, x, z, y)
inline val Vector3.xxzz: Vector4
    get() = Vector4(x, x, z, z)
inline val Vector3.xxzw: Vector4
    get() = Vector4(x, x, z, w)
inline val Vector3.xxwx: Vector4
    get() = Vector4(x, x, w, x)
inline val Vector3.xxwy: Vector4
    get() = Vector4(x, x, w, y)
inline val Vector3.xxwz: Vector4
    get() = Vector4(x, x, w, z)
inline val Vector3.xxww: Vector4
    get() = Vector4(x, x, w, w)
inline val Vector3.xyxx: Vector4
    get() = Vector4(x, y, x, x)
inline val Vector3.xyxy: Vector4
    get() = Vector4(x, y, x, y)
inline val Vector3.xyxz: Vector4
    get() = Vector4(x, y, x, z)
inline val Vector3.xyxw: Vector4
    get() = Vector4(x, y, x, w)
inline val Vector3.xyyx: Vector4
    get() = Vector4(x, y, y, x)
inline val Vector3.xyyy: Vector4
    get() = Vector4(x, y, y, y)
inline val Vector3.xyyz: Vector4
    get() = Vector4(x, y, y, z)
inline val Vector3.xyyw: Vector4
    get() = Vector4(x, y, y, w)
inline val Vector3.xyzx: Vector4
    get() = Vector4(x, y, z, x)
inline val Vector3.xyzy: Vector4
    get() = Vector4(x, y, z, y)
inline val Vector3.xyzz: Vector4
    get() = Vector4(x, y, z, z)
inline val Vector3.xyzw: Vector4
    get() = Vector4(x, y, z, w)
inline val Vector3.xywx: Vector4
    get() = Vector4(x, y, w, x)
inline val Vector3.xywy: Vector4
    get() = Vector4(x, y, w, y)
inline val Vector3.xywz: Vector4
    get() = Vector4(x, y, w, z)
inline val Vector3.xyww: Vector4
    get() = Vector4(x, y, w, w)
inline val Vector3.xzxx: Vector4
    get() = Vector4(x, z, x, x)
inline val Vector3.xzxy: Vector4
    get() = Vector4(x, z, x, y)
inline val Vector3.xzxz: Vector4
    get() = Vector4(x, z, x, z)
inline val Vector3.xzxw: Vector4
    get() = Vector4(x, z, x, w)
inline val Vector3.xzyx: Vector4
    get() = Vector4(x, z, y, x)
inline val Vector3.xzyy: Vector4
    get() = Vector4(x, z, y, y)
inline val Vector3.xzyz: Vector4
    get() = Vector4(x, z, y, z)
inline val Vector3.xzyw: Vector4
    get() = Vector4(x, z, y, w)
inline val Vector3.xzzx: Vector4
    get() = Vector4(x, z, z, x)
inline val Vector3.xzzy: Vector4
    get() = Vector4(x, z, z, y)
inline val Vector3.xzzz: Vector4
    get() = Vector4(x, z, z, z)
inline val Vector3.xzzw: Vector4
    get() = Vector4(x, z, z, w)
inline val Vector3.xzwx: Vector4
    get() = Vector4(x, z, w, x)
inline val Vector3.xzwy: Vector4
    get() = Vector4(x, z, w, y)
inline val Vector3.xzwz: Vector4
    get() = Vector4(x, z, w, z)
inline val Vector3.xzww: Vector4
    get() = Vector4(x, z, w, w)
inline val Vector3.xwxx: Vector4
    get() = Vector4(x, w, x, x)
inline val Vector3.xwxy: Vector4
    get() = Vector4(x, w, x, y)
inline val Vector3.xwxz: Vector4
    get() = Vector4(x, w, x, z)
inline val Vector3.xwxw: Vector4
    get() = Vector4(x, w, x, w)
inline val Vector3.xwyx: Vector4
    get() = Vector4(x, w, y, x)
inline val Vector3.xwyy: Vector4
    get() = Vector4(x, w, y, y)
inline val Vector3.xwyz: Vector4
    get() = Vector4(x, w, y, z)
inline val Vector3.xwyw: Vector4
    get() = Vector4(x, w, y, w)
inline val Vector3.xwzx: Vector4
    get() = Vector4(x, w, z, x)
inline val Vector3.xwzy: Vector4
    get() = Vector4(x, w, z, y)
inline val Vector3.xwzz: Vector4
    get() = Vector4(x, w, z, z)
inline val Vector3.xwzw: Vector4
    get() = Vector4(x, w, z, w)
inline val Vector3.xwwx: Vector4
    get() = Vector4(x, w, w, x)
inline val Vector3.xwwy: Vector4
    get() = Vector4(x, w, w, y)
inline val Vector3.xwwz: Vector4
    get() = Vector4(x, w, w, z)
inline val Vector3.xwww: Vector4
    get() = Vector4(x, w, w, w)
inline val Vector3.yxxx: Vector4
    get() = Vector4(y, x, x, x)
inline val Vector3.yxxy: Vector4
    get() = Vector4(y, x, x, y)
inline val Vector3.yxxz: Vector4
    get() = Vector4(y, x, x, z)
inline val Vector3.yxxw: Vector4
    get() = Vector4(y, x, x, w)
inline val Vector3.yxyx: Vector4
    get() = Vector4(y, x, y, x)
inline val Vector3.yxyy: Vector4
    get() = Vector4(y, x, y, y)
inline val Vector3.yxyz: Vector4
    get() = Vector4(y, x, y, z)
inline val Vector3.yxyw: Vector4
    get() = Vector4(y, x, y, w)
inline val Vector3.yxzx: Vector4
    get() = Vector4(y, x, z, x)
inline val Vector3.yxzy: Vector4
    get() = Vector4(y, x, z, y)
inline val Vector3.yxzz: Vector4
    get() = Vector4(y, x, z, z)
inline val Vector3.yxzw: Vector4
    get() = Vector4(y, x, z, w)
inline val Vector3.yxwx: Vector4
    get() = Vector4(y, x, w, x)
inline val Vector3.yxwy: Vector4
    get() = Vector4(y, x, w, y)
inline val Vector3.yxwz: Vector4
    get() = Vector4(y, x, w, z)
inline val Vector3.yxww: Vector4
    get() = Vector4(y, x, w, w)
inline val Vector3.yyxx: Vector4
    get() = Vector4(y, y, x, x)
inline val Vector3.yyxy: Vector4
    get() = Vector4(y, y, x, y)
inline val Vector3.yyxz: Vector4
    get() = Vector4(y, y, x, z)
inline val Vector3.yyxw: Vector4
    get() = Vector4(y, y, x, w)
inline val Vector3.yyyx: Vector4
    get() = Vector4(y, y, y, x)
inline val Vector3.yyyy: Vector4
    get() = Vector4(y, y, y, y)
inline val Vector3.yyyz: Vector4
    get() = Vector4(y, y, y, z)
inline val Vector3.yyyw: Vector4
    get() = Vector4(y, y, y, w)
inline val Vector3.yyzx: Vector4
    get() = Vector4(y, y, z, x)
inline val Vector3.yyzy: Vector4
    get() = Vector4(y, y, z, y)
inline val Vector3.yyzz: Vector4
    get() = Vector4(y, y, z, z)
inline val Vector3.yyzw: Vector4
    get() = Vector4(y, y, z, w)
inline val Vector3.yywx: Vector4
    get() = Vector4(y, y, w, x)
inline val Vector3.yywy: Vector4
    get() = Vector4(y, y, w, y)
inline val Vector3.yywz: Vector4
    get() = Vector4(y, y, w, z)
inline val Vector3.yyww: Vector4
    get() = Vector4(y, y, w, w)
inline val Vector3.yzxx: Vector4
    get() = Vector4(y, z, x, x)
inline val Vector3.yzxy: Vector4
    get() = Vector4(y, z, x, y)
inline val Vector3.yzxz: Vector4
    get() = Vector4(y, z, x, z)
inline val Vector3.yzxw: Vector4
    get() = Vector4(y, z, x, w)
inline val Vector3.yzyx: Vector4
    get() = Vector4(y, z, y, x)
inline val Vector3.yzyy: Vector4
    get() = Vector4(y, z, y, y)
inline val Vector3.yzyz: Vector4
    get() = Vector4(y, z, y, z)
inline val Vector3.yzyw: Vector4
    get() = Vector4(y, z, y, w)
inline val Vector3.yzzx: Vector4
    get() = Vector4(y, z, z, x)
inline val Vector3.yzzy: Vector4
    get() = Vector4(y, z, z, y)
inline val Vector3.yzzz: Vector4
    get() = Vector4(y, z, z, z)
inline val Vector3.yzzw: Vector4
    get() = Vector4(y, z, z, w)
inline val Vector3.yzwx: Vector4
    get() = Vector4(y, z, w, x)
inline val Vector3.yzwy: Vector4
    get() = Vector4(y, z, w, y)
inline val Vector3.yzwz: Vector4
    get() = Vector4(y, z, w, z)
inline val Vector3.yzww: Vector4
    get() = Vector4(y, z, w, w)
inline val Vector3.ywxx: Vector4
    get() = Vector4(y, w, x, x)
inline val Vector3.ywxy: Vector4
    get() = Vector4(y, w, x, y)
inline val Vector3.ywxz: Vector4
    get() = Vector4(y, w, x, z)
inline val Vector3.ywxw: Vector4
    get() = Vector4(y, w, x, w)
inline val Vector3.ywyx: Vector4
    get() = Vector4(y, w, y, x)
inline val Vector3.ywyy: Vector4
    get() = Vector4(y, w, y, y)
inline val Vector3.ywyz: Vector4
    get() = Vector4(y, w, y, z)
inline val Vector3.ywyw: Vector4
    get() = Vector4(y, w, y, w)
inline val Vector3.ywzx: Vector4
    get() = Vector4(y, w, z, x)
inline val Vector3.ywzy: Vector4
    get() = Vector4(y, w, z, y)
inline val Vector3.ywzz: Vector4
    get() = Vector4(y, w, z, z)
inline val Vector3.ywzw: Vector4
    get() = Vector4(y, w, z, w)
inline val Vector3.ywwx: Vector4
    get() = Vector4(y, w, w, x)
inline val Vector3.ywwy: Vector4
    get() = Vector4(y, w, w, y)
inline val Vector3.ywwz: Vector4
    get() = Vector4(y, w, w, z)
inline val Vector3.ywww: Vector4
    get() = Vector4(y, w, w, w)
inline val Vector3.zxxx: Vector4
    get() = Vector4(z, x, x, x)
inline val Vector3.zxxy: Vector4
    get() = Vector4(z, x, x, y)
inline val Vector3.zxxz: Vector4
    get() = Vector4(z, x, x, z)
inline val Vector3.zxxw: Vector4
    get() = Vector4(z, x, x, w)
inline val Vector3.zxyx: Vector4
    get() = Vector4(z, x, y, x)
inline val Vector3.zxyy: Vector4
    get() = Vector4(z, x, y, y)
inline val Vector3.zxyz: Vector4
    get() = Vector4(z, x, y, z)
inline val Vector3.zxyw: Vector4
    get() = Vector4(z, x, y, w)
inline val Vector3.zxzx: Vector4
    get() = Vector4(z, x, z, x)
inline val Vector3.zxzy: Vector4
    get() = Vector4(z, x, z, y)
inline val Vector3.zxzz: Vector4
    get() = Vector4(z, x, z, z)
inline val Vector3.zxzw: Vector4
    get() = Vector4(z, x, z, w)
inline val Vector3.zxwx: Vector4
    get() = Vector4(z, x, w, x)
inline val Vector3.zxwy: Vector4
    get() = Vector4(z, x, w, y)
inline val Vector3.zxwz: Vector4
    get() = Vector4(z, x, w, z)
inline val Vector3.zxww: Vector4
    get() = Vector4(z, x, w, w)
inline val Vector3.zyxx: Vector4
    get() = Vector4(z, y, x, x)
inline val Vector3.zyxy: Vector4
    get() = Vector4(z, y, x, y)
inline val Vector3.zyxz: Vector4
    get() = Vector4(z, y, x, z)
inline val Vector3.zyxw: Vector4
    get() = Vector4(z, y, x, w)
inline val Vector3.zyyx: Vector4
    get() = Vector4(z, y, y, x)
inline val Vector3.zyyy: Vector4
    get() = Vector4(z, y, y, y)
inline val Vector3.zyyz: Vector4
    get() = Vector4(z, y, y, z)
inline val Vector3.zyyw: Vector4
    get() = Vector4(z, y, y, w)
inline val Vector3.zyzx: Vector4
    get() = Vector4(z, y, z, x)
inline val Vector3.zyzy: Vector4
    get() = Vector4(z, y, z, y)
inline val Vector3.zyzz: Vector4
    get() = Vector4(z, y, z, z)
inline val Vector3.zyzw: Vector4
    get() = Vector4(z, y, z, w)
inline val Vector3.zywx: Vector4
    get() = Vector4(z, y, w, x)
inline val Vector3.zywy: Vector4
    get() = Vector4(z, y, w, y)
inline val Vector3.zywz: Vector4
    get() = Vector4(z, y, w, z)
inline val Vector3.zyww: Vector4
    get() = Vector4(z, y, w, w)
inline val Vector3.zzxx: Vector4
    get() = Vector4(z, z, x, x)
inline val Vector3.zzxy: Vector4
    get() = Vector4(z, z, x, y)
inline val Vector3.zzxz: Vector4
    get() = Vector4(z, z, x, z)
inline val Vector3.zzxw: Vector4
    get() = Vector4(z, z, x, w)
inline val Vector3.zzyx: Vector4
    get() = Vector4(z, z, y, x)
inline val Vector3.zzyy: Vector4
    get() = Vector4(z, z, y, y)
inline val Vector3.zzyz: Vector4
    get() = Vector4(z, z, y, z)
inline val Vector3.zzyw: Vector4
    get() = Vector4(z, z, y, w)
inline val Vector3.zzzx: Vector4
    get() = Vector4(z, z, z, x)
inline val Vector3.zzzy: Vector4
    get() = Vector4(z, z, z, y)
inline val Vector3.zzzz: Vector4
    get() = Vector4(z, z, z, z)
inline val Vector3.zzzw: Vector4
    get() = Vector4(z, z, z, w)
inline val Vector3.zzwx: Vector4
    get() = Vector4(z, z, w, x)
inline val Vector3.zzwy: Vector4
    get() = Vector4(z, z, w, y)
inline val Vector3.zzwz: Vector4
    get() = Vector4(z, z, w, z)
inline val Vector3.zzww: Vector4
    get() = Vector4(z, z, w, w)
inline val Vector3.zwxx: Vector4
    get() = Vector4(z, w, x, x)
inline val Vector3.zwxy: Vector4
    get() = Vector4(z, w, x, y)
inline val Vector3.zwxz: Vector4
    get() = Vector4(z, w, x, z)
inline val Vector3.zwxw: Vector4
    get() = Vector4(z, w, x, w)
inline val Vector3.zwyx: Vector4
    get() = Vector4(z, w, y, x)
inline val Vector3.zwyy: Vector4
    get() = Vector4(z, w, y, y)
inline val Vector3.zwyz: Vector4
    get() = Vector4(z, w, y, z)
inline val Vector3.zwyw: Vector4
    get() = Vector4(z, w, y, w)
inline val Vector3.zwzx: Vector4
    get() = Vector4(z, w, z, x)
inline val Vector3.zwzy: Vector4
    get() = Vector4(z, w, z, y)
inline val Vector3.zwzz: Vector4
    get() = Vector4(z, w, z, z)
inline val Vector3.zwzw: Vector4
    get() = Vector4(z, w, z, w)
inline val Vector3.zwwx: Vector4
    get() = Vector4(z, w, w, x)
inline val Vector3.zwwy: Vector4
    get() = Vector4(z, w, w, y)
inline val Vector3.zwwz: Vector4
    get() = Vector4(z, w, w, z)
inline val Vector3.zwww: Vector4
    get() = Vector4(z, w, w, w)
inline val Vector3.wxxx: Vector4
    get() = Vector4(w, x, x, x)
inline val Vector3.wxxy: Vector4
    get() = Vector4(w, x, x, y)
inline val Vector3.wxxz: Vector4
    get() = Vector4(w, x, x, z)
inline val Vector3.wxxw: Vector4
    get() = Vector4(w, x, x, w)
inline val Vector3.wxyx: Vector4
    get() = Vector4(w, x, y, x)
inline val Vector3.wxyy: Vector4
    get() = Vector4(w, x, y, y)
inline val Vector3.wxyz: Vector4
    get() = Vector4(w, x, y, z)
inline val Vector3.wxyw: Vector4
    get() = Vector4(w, x, y, w)
inline val Vector3.wxzx: Vector4
    get() = Vector4(w, x, z, x)
inline val Vector3.wxzy: Vector4
    get() = Vector4(w, x, z, y)
inline val Vector3.wxzz: Vector4
    get() = Vector4(w, x, z, z)
inline val Vector3.wxzw: Vector4
    get() = Vector4(w, x, z, w)
inline val Vector3.wxwx: Vector4
    get() = Vector4(w, x, w, x)
inline val Vector3.wxwy: Vector4
    get() = Vector4(w, x, w, y)
inline val Vector3.wxwz: Vector4
    get() = Vector4(w, x, w, z)
inline val Vector3.wxww: Vector4
    get() = Vector4(w, x, w, w)
inline val Vector3.wyxx: Vector4
    get() = Vector4(w, y, x, x)
inline val Vector3.wyxy: Vector4
    get() = Vector4(w, y, x, y)
inline val Vector3.wyxz: Vector4
    get() = Vector4(w, y, x, z)
inline val Vector3.wyxw: Vector4
    get() = Vector4(w, y, x, w)
inline val Vector3.wyyx: Vector4
    get() = Vector4(w, y, y, x)
inline val Vector3.wyyy: Vector4
    get() = Vector4(w, y, y, y)
inline val Vector3.wyyz: Vector4
    get() = Vector4(w, y, y, z)
inline val Vector3.wyyw: Vector4
    get() = Vector4(w, y, y, w)
inline val Vector3.wyzx: Vector4
    get() = Vector4(w, y, z, x)
inline val Vector3.wyzy: Vector4
    get() = Vector4(w, y, z, y)
inline val Vector3.wyzz: Vector4
    get() = Vector4(w, y, z, z)
inline val Vector3.wyzw: Vector4
    get() = Vector4(w, y, z, w)
inline val Vector3.wywx: Vector4
    get() = Vector4(w, y, w, x)
inline val Vector3.wywy: Vector4
    get() = Vector4(w, y, w, y)
inline val Vector3.wywz: Vector4
    get() = Vector4(w, y, w, z)
inline val Vector3.wyww: Vector4
    get() = Vector4(w, y, w, w)
inline val Vector3.wzxx: Vector4
    get() = Vector4(w, z, x, x)
inline val Vector3.wzxy: Vector4
    get() = Vector4(w, z, x, y)
inline val Vector3.wzxz: Vector4
    get() = Vector4(w, z, x, z)
inline val Vector3.wzxw: Vector4
    get() = Vector4(w, z, x, w)
inline val Vector3.wzyx: Vector4
    get() = Vector4(w, z, y, x)
inline val Vector3.wzyy: Vector4
    get() = Vector4(w, z, y, y)
inline val Vector3.wzyz: Vector4
    get() = Vector4(w, z, y, z)
inline val Vector3.wzyw: Vector4
    get() = Vector4(w, z, y, w)
inline val Vector3.wzzx: Vector4
    get() = Vector4(w, z, z, x)
inline val Vector3.wzzy: Vector4
    get() = Vector4(w, z, z, y)
inline val Vector3.wzzz: Vector4
    get() = Vector4(w, z, z, z)
inline val Vector3.wzzw: Vector4
    get() = Vector4(w, z, z, w)
inline val Vector3.wzwx: Vector4
    get() = Vector4(w, z, w, x)
inline val Vector3.wzwy: Vector4
    get() = Vector4(w, z, w, y)
inline val Vector3.wzwz: Vector4
    get() = Vector4(w, z, w, z)
inline val Vector3.wzww: Vector4
    get() = Vector4(w, z, w, w)
inline val Vector3.wwxx: Vector4
    get() = Vector4(w, w, x, x)
inline val Vector3.wwxy: Vector4
    get() = Vector4(w, w, x, y)
inline val Vector3.wwxz: Vector4
    get() = Vector4(w, w, x, z)
inline val Vector3.wwxw: Vector4
    get() = Vector4(w, w, x, w)
inline val Vector3.wwyx: Vector4
    get() = Vector4(w, w, y, x)
inline val Vector3.wwyy: Vector4
    get() = Vector4(w, w, y, y)
inline val Vector3.wwyz: Vector4
    get() = Vector4(w, w, y, z)
inline val Vector3.wwyw: Vector4
    get() = Vector4(w, w, y, w)
inline val Vector3.wwzx: Vector4
    get() = Vector4(w, w, z, x)
inline val Vector3.wwzy: Vector4
    get() = Vector4(w, w, z, y)
inline val Vector3.wwzz: Vector4
    get() = Vector4(w, w, z, z)
inline val Vector3.wwzw: Vector4
    get() = Vector4(w, w, z, w)
inline val Vector3.wwwx: Vector4
    get() = Vector4(w, w, w, x)
inline val Vector3.wwwy: Vector4
    get() = Vector4(w, w, w, y)
inline val Vector3.wwwz: Vector4
    get() = Vector4(w, w, w, z)
inline val Vector3.wwww: Vector4
    get() = Vector4(w, w, w, w)