package org.rufousengine.math


// --- Vector2 ---------------------------------------------------------------------------------------------------------


inline val Vector2.xx: Vector2
    get() = Vector2(x, x)
inline val Vector2.xy: Vector2
    get() = Vector2(x, y)
inline val Vector2.xz: Vector2
    get() = Vector2(x, z)
inline val Vector2.xw: Vector2
    get() = Vector2(x, w)
inline val Vector2.yx: Vector2
    get() = Vector2(y, x)
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


// --- Vector3 ---------------------------------------------------------------------------------------------------------


inline val Vector3.xx: Vector2
    get() = Vector2(x, x)
inline val Vector3.xy: Vector2
    get() = Vector2(x, y)
inline val Vector3.xz: Vector2
    get() = Vector2(x, z)
inline val Vector3.xw: Vector2
    get() = Vector2(x, w)
inline val Vector3.yx: Vector2
    get() = Vector2(y, x)
inline val Vector3.yy: Vector2
    get() = Vector2(y, y)
inline val Vector3.yz: Vector2
    get() = Vector2(y, z)
inline val Vector3.yw: Vector2
    get() = Vector2(y, w)
inline val Vector3.zx: Vector2
    get() = Vector2(z, x)
inline val Vector3.zy: Vector2
    get() = Vector2(z, y)
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
inline val Vector3.xyz: Vector3
    get() = Vector3(x, y, z)
inline val Vector3.xyw: Vector3
    get() = Vector3(x, y, w)
inline val Vector3.xzx: Vector3
    get() = Vector3(x, z, x)
inline val Vector3.xzy: Vector3
    get() = Vector3(x, z, y)
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
inline val Vector3.yxz: Vector3
    get() = Vector3(y, x, z)
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
inline val Vector3.yzx: Vector3
    get() = Vector3(y, z, x)
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
inline val Vector3.zxy: Vector3
    get() = Vector3(z, x, y)
inline val Vector3.zxz: Vector3
    get() = Vector3(z, x, z)
inline val Vector3.zxw: Vector3
    get() = Vector3(z, x, w)
inline val Vector3.zyx: Vector3
    get() = Vector3(z, y, x)
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


// --- Vector4 ---------------------------------------------------------------------------------------------------------


inline val Vector4.xx: Vector2
    get() = Vector2(x, x)
inline val Vector4.xy: Vector2
    get() = Vector2(x, y)
inline val Vector4.xz: Vector2
    get() = Vector2(x, z)
inline val Vector4.xw: Vector2
    get() = Vector2(x, w)
inline val Vector4.yx: Vector2
    get() = Vector2(y, x)
inline val Vector4.yy: Vector2
    get() = Vector2(y, y)
inline val Vector4.yz: Vector2
    get() = Vector2(y, z)
inline val Vector4.yw: Vector2
    get() = Vector2(y, w)
inline val Vector4.zx: Vector2
    get() = Vector2(z, x)
inline val Vector4.zy: Vector2
    get() = Vector2(z, y)
inline val Vector4.zz: Vector2
    get() = Vector2(z, z)
inline val Vector4.zw: Vector2
    get() = Vector2(z, w)
inline val Vector4.wx: Vector2
    get() = Vector2(w, x)
inline val Vector4.wy: Vector2
    get() = Vector2(w, y)
inline val Vector4.wz: Vector2
    get() = Vector2(w, z)
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
inline val Vector4.xyz: Vector3
    get() = Vector3(x, y, z)
inline val Vector4.xyw: Vector3
    get() = Vector3(x, y, w)
inline val Vector4.xzx: Vector3
    get() = Vector3(x, z, x)
inline val Vector4.xzy: Vector3
    get() = Vector3(x, z, y)
inline val Vector4.xzz: Vector3
    get() = Vector3(x, z, z)
inline val Vector4.xzw: Vector3
    get() = Vector3(x, z, w)
inline val Vector4.xwx: Vector3
    get() = Vector3(x, w, x)
inline val Vector4.xwy: Vector3
    get() = Vector3(x, w, y)
inline val Vector4.xwz: Vector3
    get() = Vector3(x, w, z)
inline val Vector4.xww: Vector3
    get() = Vector3(x, w, w)
inline val Vector4.yxx: Vector3
    get() = Vector3(y, x, x)
inline val Vector4.yxy: Vector3
    get() = Vector3(y, x, y)
inline val Vector4.yxz: Vector3
    get() = Vector3(y, x, z)
inline val Vector4.yxw: Vector3
    get() = Vector3(y, x, w)
inline val Vector4.yyx: Vector3
    get() = Vector3(y, y, x)
inline val Vector4.yyy: Vector3
    get() = Vector3(y, y, y)
inline val Vector4.yyz: Vector3
    get() = Vector3(y, y, z)
inline val Vector4.yyw: Vector3
    get() = Vector3(y, y, w)
inline val Vector4.yzx: Vector3
    get() = Vector3(y, z, x)
inline val Vector4.yzy: Vector3
    get() = Vector3(y, z, y)
inline val Vector4.yzz: Vector3
    get() = Vector3(y, z, z)
inline val Vector4.yzw: Vector3
    get() = Vector3(y, z, w)
inline val Vector4.ywx: Vector3
    get() = Vector3(y, w, x)
inline val Vector4.ywy: Vector3
    get() = Vector3(y, w, y)
inline val Vector4.ywz: Vector3
    get() = Vector3(y, w, z)
inline val Vector4.yww: Vector3
    get() = Vector3(y, w, w)
inline val Vector4.zxx: Vector3
    get() = Vector3(z, x, x)
inline val Vector4.zxy: Vector3
    get() = Vector3(z, x, y)
inline val Vector4.zxz: Vector3
    get() = Vector3(z, x, z)
inline val Vector4.zxw: Vector3
    get() = Vector3(z, x, w)
inline val Vector4.zyx: Vector3
    get() = Vector3(z, y, x)
inline val Vector4.zyy: Vector3
    get() = Vector3(z, y, y)
inline val Vector4.zyz: Vector3
    get() = Vector3(z, y, z)
inline val Vector4.zyw: Vector3
    get() = Vector3(z, y, w)
inline val Vector4.zzx: Vector3
    get() = Vector3(z, z, x)
inline val Vector4.zzy: Vector3
    get() = Vector3(z, z, y)
inline val Vector4.zzz: Vector3
    get() = Vector3(z, z, z)
inline val Vector4.zzw: Vector3
    get() = Vector3(z, z, w)
inline val Vector4.zwx: Vector3
    get() = Vector3(z, w, x)
inline val Vector4.zwy: Vector3
    get() = Vector3(z, w, y)
inline val Vector4.zwz: Vector3
    get() = Vector3(z, w, z)
inline val Vector4.zww: Vector3
    get() = Vector3(z, w, w)
inline val Vector4.wxx: Vector3
    get() = Vector3(w, x, x)
inline val Vector4.wxy: Vector3
    get() = Vector3(w, x, y)
inline val Vector4.wxz: Vector3
    get() = Vector3(w, x, z)
inline val Vector4.wxw: Vector3
    get() = Vector3(w, x, w)
inline val Vector4.wyx: Vector3
    get() = Vector3(w, y, x)
inline val Vector4.wyy: Vector3
    get() = Vector3(w, y, y)
inline val Vector4.wyz: Vector3
    get() = Vector3(w, y, z)
inline val Vector4.wyw: Vector3
    get() = Vector3(w, y, w)
inline val Vector4.wzx: Vector3
    get() = Vector3(w, z, x)
inline val Vector4.wzy: Vector3
    get() = Vector3(w, z, y)
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
inline val Vector4.xyzw: Vector4
    get() = Vector4(x, y, z, w)
inline val Vector4.xywx: Vector4
    get() = Vector4(x, y, w, x)
inline val Vector4.xywy: Vector4
    get() = Vector4(x, y, w, y)
inline val Vector4.xywz: Vector4
    get() = Vector4(x, y, w, z)
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
inline val Vector4.xzyw: Vector4
    get() = Vector4(x, z, y, w)
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
inline val Vector4.xzwy: Vector4
    get() = Vector4(x, z, w, y)
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
inline val Vector4.xwyz: Vector4
    get() = Vector4(x, w, y, z)
inline val Vector4.xwyw: Vector4
    get() = Vector4(x, w, y, w)
inline val Vector4.xwzx: Vector4
    get() = Vector4(x, w, z, x)
inline val Vector4.xwzy: Vector4
    get() = Vector4(x, w, z, y)
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
inline val Vector4.yxzw: Vector4
    get() = Vector4(y, x, z, w)
inline val Vector4.yxwx: Vector4
    get() = Vector4(y, x, w, x)
inline val Vector4.yxwy: Vector4
    get() = Vector4(y, x, w, y)
inline val Vector4.yxwz: Vector4
    get() = Vector4(y, x, w, z)
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
inline val Vector4.yzxw: Vector4
    get() = Vector4(y, z, x, w)
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
inline val Vector4.yzwx: Vector4
    get() = Vector4(y, z, w, x)
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
inline val Vector4.ywxz: Vector4
    get() = Vector4(y, w, x, z)
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
inline val Vector4.ywzx: Vector4
    get() = Vector4(y, w, z, x)
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
inline val Vector4.zxyw: Vector4
    get() = Vector4(z, x, y, w)
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
inline val Vector4.zxwy: Vector4
    get() = Vector4(z, x, w, y)
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
inline val Vector4.zyxw: Vector4
    get() = Vector4(z, y, x, w)
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
inline val Vector4.zywx: Vector4
    get() = Vector4(z, y, w, x)
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
inline val Vector4.zwxy: Vector4
    get() = Vector4(z, w, x, y)
inline val Vector4.zwxz: Vector4
    get() = Vector4(z, w, x, z)
inline val Vector4.zwxw: Vector4
    get() = Vector4(z, w, x, w)
inline val Vector4.zwyx: Vector4
    get() = Vector4(z, w, y, x)
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
inline val Vector4.wxyz: Vector4
    get() = Vector4(w, x, y, z)
inline val Vector4.wxyw: Vector4
    get() = Vector4(w, x, y, w)
inline val Vector4.wxzx: Vector4
    get() = Vector4(w, x, z, x)
inline val Vector4.wxzy: Vector4
    get() = Vector4(w, x, z, y)
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
inline val Vector4.wyxz: Vector4
    get() = Vector4(w, y, x, z)
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
inline val Vector4.wyzx: Vector4
    get() = Vector4(w, y, z, x)
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
inline val Vector4.wzxy: Vector4
    get() = Vector4(w, z, x, y)
inline val Vector4.wzxz: Vector4
    get() = Vector4(w, z, x, z)
inline val Vector4.wzxw: Vector4
    get() = Vector4(w, z, x, w)
inline val Vector4.wzyx: Vector4
    get() = Vector4(w, z, y, x)
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


// --- Point2D ---------------------------------------------------------------------------------------------------------


inline val Point2D.xx: Point2D
    get() = Point2D(x, x)
inline val Point2D.xy: Point2D
    get() = Point2D(x, y)
inline val Point2D.xz: Point2D
    get() = Point2D(x, z)
inline val Point2D.yx: Point2D
    get() = Point2D(y, x)
inline val Point2D.yy: Point2D
    get() = Point2D(y, y)
inline val Point2D.yz: Point2D
    get() = Point2D(y, z)
inline val Point2D.zx: Point2D
    get() = Point2D(z, x)
inline val Point2D.zy: Point2D
    get() = Point2D(z, y)
inline val Point2D.zz: Point2D
    get() = Point2D(z, z)

inline val Point2D.xxx: Point3D
    get() = Point3D(x, x, x)
inline val Point2D.xxy: Point3D
    get() = Point3D(x, x, y)
inline val Point2D.xxz: Point3D
    get() = Point3D(x, x, z)
inline val Point2D.xyx: Point3D
    get() = Point3D(x, y, x)
inline val Point2D.xyy: Point3D
    get() = Point3D(x, y, y)
inline val Point2D.xyz: Point3D
    get() = Point3D(x, y, z)
inline val Point2D.xzx: Point3D
    get() = Point3D(x, z, x)
inline val Point2D.xzy: Point3D
    get() = Point3D(x, z, y)
inline val Point2D.xzz: Point3D
    get() = Point3D(x, z, z)
inline val Point2D.yxx: Point3D
    get() = Point3D(y, x, x)
inline val Point2D.yxy: Point3D
    get() = Point3D(y, x, y)
inline val Point2D.yxz: Point3D
    get() = Point3D(y, x, z)
inline val Point2D.yyx: Point3D
    get() = Point3D(y, y, x)
inline val Point2D.yyy: Point3D
    get() = Point3D(y, y, y)
inline val Point2D.yyz: Point3D
    get() = Point3D(y, y, z)
inline val Point2D.yzx: Point3D
    get() = Point3D(y, z, x)
inline val Point2D.yzy: Point3D
    get() = Point3D(y, z, y)
inline val Point2D.yzz: Point3D
    get() = Point3D(y, z, z)
inline val Point2D.zxx: Point3D
    get() = Point3D(z, x, x)
inline val Point2D.zxy: Point3D
    get() = Point3D(z, x, y)
inline val Point2D.zxz: Point3D
    get() = Point3D(z, x, z)
inline val Point2D.zyx: Point3D
    get() = Point3D(z, y, x)
inline val Point2D.zyy: Point3D
    get() = Point3D(z, y, y)
inline val Point2D.zyz: Point3D
    get() = Point3D(z, y, z)
inline val Point2D.zzx: Point3D
    get() = Point3D(z, z, x)
inline val Point2D.zzy: Point3D
    get() = Point3D(z, z, y)
inline val Point2D.zzz: Point3D
    get() = Point3D(z, z, z)


// --- Point3D ---------------------------------------------------------------------------------------------------------


inline val Point3D.xx: Point2D
    get() = Point2D(x, x)
inline val Point3D.xy: Point2D
    get() = Point2D(x, y)
inline val Point3D.xz: Point2D
    get() = Point2D(x, z)
inline val Point3D.yx: Point2D
    get() = Point2D(y, x)
inline val Point3D.yy: Point2D
    get() = Point2D(y, y)
inline val Point3D.yz: Point2D
    get() = Point2D(y, z)
inline val Point3D.zx: Point2D
    get() = Point2D(z, x)
inline val Point3D.zy: Point2D
    get() = Point2D(z, y)
inline val Point3D.zz: Point2D
    get() = Point2D(z, z)

inline val Point3D.xxx: Point3D
    get() = Point3D(x, x, x)
inline val Point3D.xxy: Point3D
    get() = Point3D(x, x, y)
inline val Point3D.xxz: Point3D
    get() = Point3D(x, x, z)
inline val Point3D.xyx: Point3D
    get() = Point3D(x, y, x)
inline val Point3D.xyy: Point3D
    get() = Point3D(x, y, y)
inline val Point3D.xyz: Point3D
    get() = Point3D(x, y, z)
inline val Point3D.xzx: Point3D
    get() = Point3D(x, z, x)
inline val Point3D.xzy: Point3D
    get() = Point3D(x, z, y)
inline val Point3D.xzz: Point3D
    get() = Point3D(x, z, z)
inline val Point3D.yxx: Point3D
    get() = Point3D(y, x, x)
inline val Point3D.yxy: Point3D
    get() = Point3D(y, x, y)
inline val Point3D.yxz: Point3D
    get() = Point3D(y, x, z)
inline val Point3D.yyx: Point3D
    get() = Point3D(y, y, x)
inline val Point3D.yyy: Point3D
    get() = Point3D(y, y, y)
inline val Point3D.yyz: Point3D
    get() = Point3D(y, y, z)
inline val Point3D.yzx: Point3D
    get() = Point3D(y, z, x)
inline val Point3D.yzy: Point3D
    get() = Point3D(y, z, y)
inline val Point3D.yzz: Point3D
    get() = Point3D(y, z, z)
inline val Point3D.zxx: Point3D
    get() = Point3D(z, x, x)
inline val Point3D.zxy: Point3D
    get() = Point3D(z, x, y)
inline val Point3D.zxz: Point3D
    get() = Point3D(z, x, z)
inline val Point3D.zyx: Point3D
    get() = Point3D(z, y, x)
inline val Point3D.zyy: Point3D
    get() = Point3D(z, y, y)
inline val Point3D.zyz: Point3D
    get() = Point3D(z, y, z)
inline val Point3D.zzx: Point3D
    get() = Point3D(z, z, x)
inline val Point3D.zzy: Point3D
    get() = Point3D(z, z, y)
inline val Point3D.zzz: Point3D
    get() = Point3D(z, z, z)