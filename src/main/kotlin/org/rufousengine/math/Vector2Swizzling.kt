package org.rufousengine.math

inline val Vector2.xx: Vector2
    get() = Vector2(x, x)
inline var Vector2.xy: Vector2
    get() = Vector2(x, y)
    set(value) {
        x = value.x
        y = value.y
    }
inline val Vector2.xz: Vector2
    get() = Vector2(x, z)
inline val Vector2.xw: Vector2
    get() = Vector2(x, w)
inline var Vector2.yx: Vector2
    get() = Vector2(y, x)
    set(value) {
        y = value.x
        x = value.y
    }
inline val Vector2.yy: Vector2
    get() = Vector2(y, y)
inline val Vector2.yz: Vector2
    get() = Vector2(y, z)
inline val Vector2.yw: Vector2
    get() = Vector2(y, w)
inline val Vector2.zx: Vector2
    get() = Vector2(z, x)
inline val Vector2.zy: Vector2
    get() = Vector2(z, y)
inline val Vector2.zz: Vector2
    get() = Vector2(z, z)
inline val Vector2.zw: Vector2
    get() = Vector2(z, w)
inline val Vector2.wx: Vector2
    get() = Vector2(w, x)
inline val Vector2.wy: Vector2
    get() = Vector2(w, y)
inline val Vector2.wz: Vector2
    get() = Vector2(w, z)
inline val Vector2.ww: Vector2
    get() = Vector2(w, w)

inline val Vector2.xxx: Vector3
    get() = Vector3(x, x, x)
inline val Vector2.xxy: Vector3
    get() = Vector3(x, x, y)
inline val Vector2.xxz: Vector3
    get() = Vector3(x, x, z)
inline val Vector2.xxw: Vector3
    get() = Vector3(x, x, w)
inline val Vector2.xyx: Vector3
    get() = Vector3(x, y, x)
inline val Vector2.xyy: Vector3
    get() = Vector3(x, y, y)
inline val Vector2.xyz: Vector3
    get() = Vector3(x, y, z)
inline val Vector2.xyw: Vector3
    get() = Vector3(x, y, w)
inline val Vector2.xzx: Vector3
    get() = Vector3(x, z, x)
inline val Vector2.xzy: Vector3
    get() = Vector3(x, z, y)
inline val Vector2.xzz: Vector3
    get() = Vector3(x, z, z)
inline val Vector2.xzw: Vector3
    get() = Vector3(x, z, w)
inline val Vector2.xwx: Vector3
    get() = Vector3(x, w, x)
inline val Vector2.xwy: Vector3
    get() = Vector3(x, w, y)
inline val Vector2.xwz: Vector3
    get() = Vector3(x, w, z)
inline val Vector2.xww: Vector3
    get() = Vector3(x, w, w)
inline val Vector2.yxx: Vector3
    get() = Vector3(y, x, x)
inline val Vector2.yxy: Vector3
    get() = Vector3(y, x, y)
inline val Vector2.yxz: Vector3
    get() = Vector3(y, x, z)
inline val Vector2.yxw: Vector3
    get() = Vector3(y, x, w)
inline val Vector2.yyx: Vector3
    get() = Vector3(y, y, x)
inline val Vector2.yyy: Vector3
    get() = Vector3(y, y, y)
inline val Vector2.yyz: Vector3
    get() = Vector3(y, y, z)
inline val Vector2.yyw: Vector3
    get() = Vector3(y, y, w)
inline val Vector2.yzx: Vector3
    get() = Vector3(y, z, x)
inline val Vector2.yzy: Vector3
    get() = Vector3(y, z, y)
inline val Vector2.yzz: Vector3
    get() = Vector3(y, z, z)
inline val Vector2.yzw: Vector3
    get() = Vector3(y, z, w)
inline val Vector2.ywx: Vector3
    get() = Vector3(y, w, x)
inline val Vector2.ywy: Vector3
    get() = Vector3(y, w, y)
inline val Vector2.ywz: Vector3
    get() = Vector3(y, w, z)
inline val Vector2.yww: Vector3
    get() = Vector3(y, w, w)
inline val Vector2.zxx: Vector3
    get() = Vector3(z, x, x)
inline val Vector2.zxy: Vector3
    get() = Vector3(z, x, y)
inline val Vector2.zxz: Vector3
    get() = Vector3(z, x, z)
inline val Vector2.zxw: Vector3
    get() = Vector3(z, x, w)
inline val Vector2.zyx: Vector3
    get() = Vector3(z, y, x)
inline val Vector2.zyy: Vector3
    get() = Vector3(z, y, y)
inline val Vector2.zyz: Vector3
    get() = Vector3(z, y, z)
inline val Vector2.zyw: Vector3
    get() = Vector3(z, y, w)
inline val Vector2.zzx: Vector3
    get() = Vector3(z, z, x)
inline val Vector2.zzy: Vector3
    get() = Vector3(z, z, y)
inline val Vector2.zzz: Vector3
    get() = Vector3(z, z, z)
inline val Vector2.zzw: Vector3
    get() = Vector3(z, z, w)
inline val Vector2.zwx: Vector3
    get() = Vector3(z, w, x)
inline val Vector2.zwy: Vector3
    get() = Vector3(z, w, y)
inline val Vector2.zwz: Vector3
    get() = Vector3(z, w, z)
inline val Vector2.zww: Vector3
    get() = Vector3(z, w, w)
inline val Vector2.wxx: Vector3
    get() = Vector3(w, x, x)
inline val Vector2.wxy: Vector3
    get() = Vector3(w, x, y)
inline val Vector2.wxz: Vector3
    get() = Vector3(w, x, z)
inline val Vector2.wxw: Vector3
    get() = Vector3(w, x, w)
inline val Vector2.wyx: Vector3
    get() = Vector3(w, y, x)
inline val Vector2.wyy: Vector3
    get() = Vector3(w, y, y)
inline val Vector2.wyz: Vector3
    get() = Vector3(w, y, z)
inline val Vector2.wyw: Vector3
    get() = Vector3(w, y, w)
inline val Vector2.wzx: Vector3
    get() = Vector3(w, z, x)
inline val Vector2.wzy: Vector3
    get() = Vector3(w, z, y)
inline val Vector2.wzz: Vector3
    get() = Vector3(w, z, z)
inline val Vector2.wzw: Vector3
    get() = Vector3(w, z, w)
inline val Vector2.wwx: Vector3
    get() = Vector3(w, w, x)
inline val Vector2.wwy: Vector3
    get() = Vector3(w, w, w)
inline val Vector2.wwz: Vector3
    get() = Vector3(w, w, w)
inline val Vector2.www: Vector3
    get() = Vector3(w, w, w)

inline val Vector2.xxxx: Vector4
    get() = Vector4(x, x, x, x)
inline val Vector2.xxxy: Vector4
    get() = Vector4(x, x, x, y)
inline val Vector2.xxxz: Vector4
    get() = Vector4(x, x, x, z)
inline val Vector2.xxxw: Vector4
    get() = Vector4(x, x, x, w)
inline val Vector2.xxyx: Vector4
    get() = Vector4(x, x, y, x)
inline val Vector2.xxyy: Vector4
    get() = Vector4(x, x, y, y)
inline val Vector2.xxyz: Vector4
    get() = Vector4(x, x, y, z)
inline val Vector2.xxyw: Vector4
    get() = Vector4(x, x, y, w)
inline val Vector2.xxzx: Vector4
    get() = Vector4(x, x, z, x)
inline val Vector2.xxzy: Vector4
    get() = Vector4(x, x, z, y)
inline val Vector2.xxzz: Vector4
    get() = Vector4(x, x, z, z)
inline val Vector2.xxzw: Vector4
    get() = Vector4(x, x, z, w)
inline val Vector2.xxwx: Vector4
    get() = Vector4(x, x, w, x)
inline val Vector2.xxwy: Vector4
    get() = Vector4(x, x, w, y)
inline val Vector2.xxwz: Vector4
    get() = Vector4(x, x, w, z)
inline val Vector2.xxww: Vector4
    get() = Vector4(x, x, w, w)
inline val Vector2.xyxx: Vector4
    get() = Vector4(x, y, x, x)
inline val Vector2.xyxy: Vector4
    get() = Vector4(x, y, x, y)
inline val Vector2.xyxz: Vector4
    get() = Vector4(x, y, x, z)
inline val Vector2.xyxw: Vector4
    get() = Vector4(x, y, x, w)
inline val Vector2.xyyx: Vector4
    get() = Vector4(x, y, y, x)
inline val Vector2.xyyy: Vector4
    get() = Vector4(x, y, y, y)
inline val Vector2.xyyz: Vector4
    get() = Vector4(x, y, y, z)
inline val Vector2.xyyw: Vector4
    get() = Vector4(x, y, y, w)
inline val Vector2.xyzx: Vector4
    get() = Vector4(x, y, z, x)
inline val Vector2.xyzy: Vector4
    get() = Vector4(x, y, z, y)
inline val Vector2.xyzz: Vector4
    get() = Vector4(x, y, z, z)
inline val Vector2.xyzw: Vector4
    get() = Vector4(x, y, z, w)
inline val Vector2.xywx: Vector4
    get() = Vector4(x, y, w, x)
inline val Vector2.xywy: Vector4
    get() = Vector4(x, y, w, y)
inline val Vector2.xywz: Vector4
    get() = Vector4(x, y, w, z)
inline val Vector2.xyww: Vector4
    get() = Vector4(x, y, w, w)
inline val Vector2.xzxx: Vector4
    get() = Vector4(x, z, x, x)
inline val Vector2.xzxy: Vector4
    get() = Vector4(x, z, x, y)
inline val Vector2.xzxz: Vector4
    get() = Vector4(x, z, x, z)
inline val Vector2.xzxw: Vector4
    get() = Vector4(x, z, x, w)
inline val Vector2.xzyx: Vector4
    get() = Vector4(x, z, y, x)
inline val Vector2.xzyy: Vector4
    get() = Vector4(x, z, y, y)
inline val Vector2.xzyz: Vector4
    get() = Vector4(x, z, y, z)
inline val Vector2.xzyw: Vector4
    get() = Vector4(x, z, y, w)
inline val Vector2.xzzx: Vector4
    get() = Vector4(x, z, z, x)
inline val Vector2.xzzy: Vector4
    get() = Vector4(x, z, z, y)
inline val Vector2.xzzz: Vector4
    get() = Vector4(x, z, z, z)
inline val Vector2.xzzw: Vector4
    get() = Vector4(x, z, z, w)
inline val Vector2.xzwx: Vector4
    get() = Vector4(x, z, w, x)
inline val Vector2.xzwy: Vector4
    get() = Vector4(x, z, w, y)
inline val Vector2.xzwz: Vector4
    get() = Vector4(x, z, w, z)
inline val Vector2.xzww: Vector4
    get() = Vector4(x, z, w, w)
inline val Vector2.xwxx: Vector4
    get() = Vector4(x, w, x, x)
inline val Vector2.xwxy: Vector4
    get() = Vector4(x, w, x, y)
inline val Vector2.xwxz: Vector4
    get() = Vector4(x, w, x, z)
inline val Vector2.xwxw: Vector4
    get() = Vector4(x, w, x, w)
inline val Vector2.xwyx: Vector4
    get() = Vector4(x, w, y, x)
inline val Vector2.xwyy: Vector4
    get() = Vector4(x, w, y, y)
inline val Vector2.xwyz: Vector4
    get() = Vector4(x, w, y, z)
inline val Vector2.xwyw: Vector4
    get() = Vector4(x, w, y, w)
inline val Vector2.xwzx: Vector4
    get() = Vector4(x, w, z, x)
inline val Vector2.xwzy: Vector4
    get() = Vector4(x, w, z, y)
inline val Vector2.xwzz: Vector4
    get() = Vector4(x, w, z, z)
inline val Vector2.xwzw: Vector4
    get() = Vector4(x, w, z, w)
inline val Vector2.xwwx: Vector4
    get() = Vector4(x, w, w, x)
inline val Vector2.xwwy: Vector4
    get() = Vector4(x, w, w, y)
inline val Vector2.xwwz: Vector4
    get() = Vector4(x, w, w, z)
inline val Vector2.xwww: Vector4
    get() = Vector4(x, w, w, w)
inline val Vector2.yxxx: Vector4
    get() = Vector4(y, x, x, x)
inline val Vector2.yxxy: Vector4
    get() = Vector4(y, x, x, y)
inline val Vector2.yxxz: Vector4
    get() = Vector4(y, x, x, z)
inline val Vector2.yxxw: Vector4
    get() = Vector4(y, x, x, w)
inline val Vector2.yxyx: Vector4
    get() = Vector4(y, x, y, x)
inline val Vector2.yxyy: Vector4
    get() = Vector4(y, x, y, y)
inline val Vector2.yxyz: Vector4
    get() = Vector4(y, x, y, z)
inline val Vector2.yxyw: Vector4
    get() = Vector4(y, x, y, w)
inline val Vector2.yxzx: Vector4
    get() = Vector4(y, x, z, x)
inline val Vector2.yxzy: Vector4
    get() = Vector4(y, x, z, y)
inline val Vector2.yxzz: Vector4
    get() = Vector4(y, x, z, z)
inline val Vector2.yxzw: Vector4
    get() = Vector4(y, x, z, w)
inline val Vector2.yxwx: Vector4
    get() = Vector4(y, x, w, x)
inline val Vector2.yxwy: Vector4
    get() = Vector4(y, x, w, y)
inline val Vector2.yxwz: Vector4
    get() = Vector4(y, x, w, z)
inline val Vector2.yxww: Vector4
    get() = Vector4(y, x, w, w)
inline val Vector2.yyxx: Vector4
    get() = Vector4(y, y, x, x)
inline val Vector2.yyxy: Vector4
    get() = Vector4(y, y, x, y)
inline val Vector2.yyxz: Vector4
    get() = Vector4(y, y, x, z)
inline val Vector2.yyxw: Vector4
    get() = Vector4(y, y, x, w)
inline val Vector2.yyyx: Vector4
    get() = Vector4(y, y, y, x)
inline val Vector2.yyyy: Vector4
    get() = Vector4(y, y, y, y)
inline val Vector2.yyyz: Vector4
    get() = Vector4(y, y, y, z)
inline val Vector2.yyyw: Vector4
    get() = Vector4(y, y, y, w)
inline val Vector2.yyzx: Vector4
    get() = Vector4(y, y, z, x)
inline val Vector2.yyzy: Vector4
    get() = Vector4(y, y, z, y)
inline val Vector2.yyzz: Vector4
    get() = Vector4(y, y, z, z)
inline val Vector2.yyzw: Vector4
    get() = Vector4(y, y, z, w)
inline val Vector2.yywx: Vector4
    get() = Vector4(y, y, w, x)
inline val Vector2.yywy: Vector4
    get() = Vector4(y, y, w, y)
inline val Vector2.yywz: Vector4
    get() = Vector4(y, y, w, z)
inline val Vector2.yyww: Vector4
    get() = Vector4(y, y, w, w)
inline val Vector2.yzxx: Vector4
    get() = Vector4(y, z, x, x)
inline val Vector2.yzxy: Vector4
    get() = Vector4(y, z, x, y)
inline val Vector2.yzxz: Vector4
    get() = Vector4(y, z, x, z)
inline val Vector2.yzxw: Vector4
    get() = Vector4(y, z, x, w)
inline val Vector2.yzyx: Vector4
    get() = Vector4(y, z, y, x)
inline val Vector2.yzyy: Vector4
    get() = Vector4(y, z, y, y)
inline val Vector2.yzyz: Vector4
    get() = Vector4(y, z, y, z)
inline val Vector2.yzyw: Vector4
    get() = Vector4(y, z, y, w)
inline val Vector2.yzzx: Vector4
    get() = Vector4(y, z, z, x)
inline val Vector2.yzzy: Vector4
    get() = Vector4(y, z, z, y)
inline val Vector2.yzzz: Vector4
    get() = Vector4(y, z, z, z)
inline val Vector2.yzzw: Vector4
    get() = Vector4(y, z, z, w)
inline val Vector2.yzwx: Vector4
    get() = Vector4(y, z, w, x)
inline val Vector2.yzwy: Vector4
    get() = Vector4(y, z, w, y)
inline val Vector2.yzwz: Vector4
    get() = Vector4(y, z, w, z)
inline val Vector2.yzww: Vector4
    get() = Vector4(y, z, w, w)
inline val Vector2.ywxx: Vector4
    get() = Vector4(y, w, x, x)
inline val Vector2.ywxy: Vector4
    get() = Vector4(y, w, x, y)
inline val Vector2.ywxz: Vector4
    get() = Vector4(y, w, x, z)
inline val Vector2.ywxw: Vector4
    get() = Vector4(y, w, x, w)
inline val Vector2.ywyx: Vector4
    get() = Vector4(y, w, y, x)
inline val Vector2.ywyy: Vector4
    get() = Vector4(y, w, y, y)
inline val Vector2.ywyz: Vector4
    get() = Vector4(y, w, y, z)
inline val Vector2.ywyw: Vector4
    get() = Vector4(y, w, y, w)
inline val Vector2.ywzx: Vector4
    get() = Vector4(y, w, z, x)
inline val Vector2.ywzy: Vector4
    get() = Vector4(y, w, z, y)
inline val Vector2.ywzz: Vector4
    get() = Vector4(y, w, z, z)
inline val Vector2.ywzw: Vector4
    get() = Vector4(y, w, z, w)
inline val Vector2.ywwx: Vector4
    get() = Vector4(y, w, w, x)
inline val Vector2.ywwy: Vector4
    get() = Vector4(y, w, w, y)
inline val Vector2.ywwz: Vector4
    get() = Vector4(y, w, w, z)
inline val Vector2.ywww: Vector4
    get() = Vector4(y, w, w, w)
inline val Vector2.zxxx: Vector4
    get() = Vector4(z, x, x, x)
inline val Vector2.zxxy: Vector4
    get() = Vector4(z, x, x, y)
inline val Vector2.zxxz: Vector4
    get() = Vector4(z, x, x, z)
inline val Vector2.zxxw: Vector4
    get() = Vector4(z, x, x, w)
inline val Vector2.zxyx: Vector4
    get() = Vector4(z, x, y, x)
inline val Vector2.zxyy: Vector4
    get() = Vector4(z, x, y, y)
inline val Vector2.zxyz: Vector4
    get() = Vector4(z, x, y, z)
inline val Vector2.zxyw: Vector4
    get() = Vector4(z, x, y, w)
inline val Vector2.zxzx: Vector4
    get() = Vector4(z, x, z, x)
inline val Vector2.zxzy: Vector4
    get() = Vector4(z, x, z, y)
inline val Vector2.zxzz: Vector4
    get() = Vector4(z, x, z, z)
inline val Vector2.zxzw: Vector4
    get() = Vector4(z, x, z, w)
inline val Vector2.zxwx: Vector4
    get() = Vector4(z, x, w, x)
inline val Vector2.zxwy: Vector4
    get() = Vector4(z, x, w, y)
inline val Vector2.zxwz: Vector4
    get() = Vector4(z, x, w, z)
inline val Vector2.zxww: Vector4
    get() = Vector4(z, x, w, w)
inline val Vector2.zyxx: Vector4
    get() = Vector4(z, y, x, x)
inline val Vector2.zyxy: Vector4
    get() = Vector4(z, y, x, y)
inline val Vector2.zyxz: Vector4
    get() = Vector4(z, y, x, z)
inline val Vector2.zyxw: Vector4
    get() = Vector4(z, y, x, w)
inline val Vector2.zyyx: Vector4
    get() = Vector4(z, y, y, x)
inline val Vector2.zyyy: Vector4
    get() = Vector4(z, y, y, y)
inline val Vector2.zyyz: Vector4
    get() = Vector4(z, y, y, z)
inline val Vector2.zyyw: Vector4
    get() = Vector4(z, y, y, w)
inline val Vector2.zyzx: Vector4
    get() = Vector4(z, y, z, x)
inline val Vector2.zyzy: Vector4
    get() = Vector4(z, y, z, y)
inline val Vector2.zyzz: Vector4
    get() = Vector4(z, y, z, z)
inline val Vector2.zyzw: Vector4
    get() = Vector4(z, y, z, w)
inline val Vector2.zywx: Vector4
    get() = Vector4(z, y, w, x)
inline val Vector2.zywy: Vector4
    get() = Vector4(z, y, w, y)
inline val Vector2.zywz: Vector4
    get() = Vector4(z, y, w, z)
inline val Vector2.zyww: Vector4
    get() = Vector4(z, y, w, w)
inline val Vector2.zzxx: Vector4
    get() = Vector4(z, z, x, x)
inline val Vector2.zzxy: Vector4
    get() = Vector4(z, z, x, y)
inline val Vector2.zzxz: Vector4
    get() = Vector4(z, z, x, z)
inline val Vector2.zzxw: Vector4
    get() = Vector4(z, z, x, w)
inline val Vector2.zzyx: Vector4
    get() = Vector4(z, z, y, x)
inline val Vector2.zzyy: Vector4
    get() = Vector4(z, z, y, y)
inline val Vector2.zzyz: Vector4
    get() = Vector4(z, z, y, z)
inline val Vector2.zzyw: Vector4
    get() = Vector4(z, z, y, w)
inline val Vector2.zzzx: Vector4
    get() = Vector4(z, z, z, x)
inline val Vector2.zzzy: Vector4
    get() = Vector4(z, z, z, y)
inline val Vector2.zzzz: Vector4
    get() = Vector4(z, z, z, z)
inline val Vector2.zzzw: Vector4
    get() = Vector4(z, z, z, w)
inline val Vector2.zzwx: Vector4
    get() = Vector4(z, z, w, x)
inline val Vector2.zzwy: Vector4
    get() = Vector4(z, z, w, y)
inline val Vector2.zzwz: Vector4
    get() = Vector4(z, z, w, z)
inline val Vector2.zzww: Vector4
    get() = Vector4(z, z, w, w)
inline val Vector2.zwxx: Vector4
    get() = Vector4(z, w, x, x)
inline val Vector2.zwxy: Vector4
    get() = Vector4(z, w, x, y)
inline val Vector2.zwxz: Vector4
    get() = Vector4(z, w, x, z)
inline val Vector2.zwxw: Vector4
    get() = Vector4(z, w, x, w)
inline val Vector2.zwyx: Vector4
    get() = Vector4(z, w, y, x)
inline val Vector2.zwyy: Vector4
    get() = Vector4(z, w, y, y)
inline val Vector2.zwyz: Vector4
    get() = Vector4(z, w, y, z)
inline val Vector2.zwyw: Vector4
    get() = Vector4(z, w, y, w)
inline val Vector2.zwzx: Vector4
    get() = Vector4(z, w, z, x)
inline val Vector2.zwzy: Vector4
    get() = Vector4(z, w, z, y)
inline val Vector2.zwzz: Vector4
    get() = Vector4(z, w, z, z)
inline val Vector2.zwzw: Vector4
    get() = Vector4(z, w, z, w)
inline val Vector2.zwwx: Vector4
    get() = Vector4(z, w, w, x)
inline val Vector2.zwwy: Vector4
    get() = Vector4(z, w, w, y)
inline val Vector2.zwwz: Vector4
    get() = Vector4(z, w, w, z)
inline val Vector2.zwww: Vector4
    get() = Vector4(z, w, w, w)
inline val Vector2.wxxx: Vector4
    get() = Vector4(w, x, x, x)
inline val Vector2.wxxy: Vector4
    get() = Vector4(w, x, x, y)
inline val Vector2.wxxz: Vector4
    get() = Vector4(w, x, x, z)
inline val Vector2.wxxw: Vector4
    get() = Vector4(w, x, x, w)
inline val Vector2.wxyx: Vector4
    get() = Vector4(w, x, y, x)
inline val Vector2.wxyy: Vector4
    get() = Vector4(w, x, y, y)
inline val Vector2.wxyz: Vector4
    get() = Vector4(w, x, y, z)
inline val Vector2.wxyw: Vector4
    get() = Vector4(w, x, y, w)
inline val Vector2.wxzx: Vector4
    get() = Vector4(w, x, z, x)
inline val Vector2.wxzy: Vector4
    get() = Vector4(w, x, z, y)
inline val Vector2.wxzz: Vector4
    get() = Vector4(w, x, z, z)
inline val Vector2.wxzw: Vector4
    get() = Vector4(w, x, z, w)
inline val Vector2.wxwx: Vector4
    get() = Vector4(w, x, w, x)
inline val Vector2.wxwy: Vector4
    get() = Vector4(w, x, w, y)
inline val Vector2.wxwz: Vector4
    get() = Vector4(w, x, w, z)
inline val Vector2.wxww: Vector4
    get() = Vector4(w, x, w, w)
inline val Vector2.wyxx: Vector4
    get() = Vector4(w, y, x, x)
inline val Vector2.wyxy: Vector4
    get() = Vector4(w, y, x, y)
inline val Vector2.wyxz: Vector4
    get() = Vector4(w, y, x, z)
inline val Vector2.wyxw: Vector4
    get() = Vector4(w, y, x, w)
inline val Vector2.wyyx: Vector4
    get() = Vector4(w, y, y, x)
inline val Vector2.wyyy: Vector4
    get() = Vector4(w, y, y, y)
inline val Vector2.wyyz: Vector4
    get() = Vector4(w, y, y, z)
inline val Vector2.wyyw: Vector4
    get() = Vector4(w, y, y, w)
inline val Vector2.wyzx: Vector4
    get() = Vector4(w, y, z, x)
inline val Vector2.wyzy: Vector4
    get() = Vector4(w, y, z, y)
inline val Vector2.wyzz: Vector4
    get() = Vector4(w, y, z, z)
inline val Vector2.wyzw: Vector4
    get() = Vector4(w, y, z, w)
inline val Vector2.wywx: Vector4
    get() = Vector4(w, y, w, x)
inline val Vector2.wywy: Vector4
    get() = Vector4(w, y, w, y)
inline val Vector2.wywz: Vector4
    get() = Vector4(w, y, w, z)
inline val Vector2.wyww: Vector4
    get() = Vector4(w, y, w, w)
inline val Vector2.wzxx: Vector4
    get() = Vector4(w, z, x, x)
inline val Vector2.wzxy: Vector4
    get() = Vector4(w, z, x, y)
inline val Vector2.wzxz: Vector4
    get() = Vector4(w, z, x, z)
inline val Vector2.wzxw: Vector4
    get() = Vector4(w, z, x, w)
inline val Vector2.wzyx: Vector4
    get() = Vector4(w, z, y, x)
inline val Vector2.wzyy: Vector4
    get() = Vector4(w, z, y, y)
inline val Vector2.wzyz: Vector4
    get() = Vector4(w, z, y, z)
inline val Vector2.wzyw: Vector4
    get() = Vector4(w, z, y, w)
inline val Vector2.wzzx: Vector4
    get() = Vector4(w, z, z, x)
inline val Vector2.wzzy: Vector4
    get() = Vector4(w, z, z, y)
inline val Vector2.wzzz: Vector4
    get() = Vector4(w, z, z, z)
inline val Vector2.wzzw: Vector4
    get() = Vector4(w, z, z, w)
inline val Vector2.wzwx: Vector4
    get() = Vector4(w, z, w, x)
inline val Vector2.wzwy: Vector4
    get() = Vector4(w, z, w, y)
inline val Vector2.wzwz: Vector4
    get() = Vector4(w, z, w, z)
inline val Vector2.wzww: Vector4
    get() = Vector4(w, z, w, w)
inline val Vector2.wwxx: Vector4
    get() = Vector4(w, w, x, x)
inline val Vector2.wwxy: Vector4
    get() = Vector4(w, w, x, y)
inline val Vector2.wwxz: Vector4
    get() = Vector4(w, w, x, z)
inline val Vector2.wwxw: Vector4
    get() = Vector4(w, w, x, w)
inline val Vector2.wwyx: Vector4
    get() = Vector4(w, w, y, x)
inline val Vector2.wwyy: Vector4
    get() = Vector4(w, w, y, y)
inline val Vector2.wwyz: Vector4
    get() = Vector4(w, w, y, z)
inline val Vector2.wwyw: Vector4
    get() = Vector4(w, w, y, w)
inline val Vector2.wwzx: Vector4
    get() = Vector4(w, w, z, x)
inline val Vector2.wwzy: Vector4
    get() = Vector4(w, w, z, y)
inline val Vector2.wwzz: Vector4
    get() = Vector4(w, w, z, z)
inline val Vector2.wwzw: Vector4
    get() = Vector4(w, w, z, w)
inline val Vector2.wwwx: Vector4
    get() = Vector4(w, w, w, x)
inline val Vector2.wwwy: Vector4
    get() = Vector4(w, w, w, y)
inline val Vector2.wwwz: Vector4
    get() = Vector4(w, w, w, z)
inline val Vector2.wwww: Vector4
    get() = Vector4(w, w, w, w)