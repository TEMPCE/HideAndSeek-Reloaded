イベントファイルの例とテンプレートです。

```json5
/*
 * イベントのテンプレートです。使用する場合実際のjsonファイルではコメントはすべて削除してください。
 */
{
  "id": "イベントのid(英語推奨)",
  "name": "イベント名",
  "desc": "イベントの説明",
  "mode": "モード(Normal,Hard,Insaneの3種類から指定可能で、指定したモードかそれ以上でのみイベントが発生するようになります)",
  "for": ["hider", "seeker"],
  //イベントの内容です。
  "event": {
    //イベントタイプを指定します。タイプと中身については下で解説しています。
    "type": "effect",
    "effect": {
      "type": "speed",
      "duration": 200,
      "amplifier": 10
    }
  }
}
```
`for`はイベントが適応されるチームのリストです。マイクラのチームを使用しています。この場合逃げと鬼両方に適応されることになります。

## イベントタイプ
### `effect`
- エフェクトを付与します。
- `type` (string) \- エフェクトのタイプです。`speed`や`slowness`などのidを入れてください。(minecraft:speedなどフルのネームスペースでなく)
- `duration` (int) \- エフェクトの持続時間です。単位はtickなので、例えば10秒間の持続なら200に設定します。
- `amplifier` (int) \- エフェクトの効果の強さです。
- `ambient` (bool,optional) \- エフェクトが薄くなるらしい？
- `particles` (bool,optional) \- trueに設定するとエフェクトのパーティクルを消すことができます。
### `custom`
- `type` (string) \- カスタムイベントのタイプです。現在は`class`のみが用意されています。
### '`custom`' イベントのタイプ
- `class` Javaコードを実行します。クラスは`com.tempce.hideandseek.core.event.custom.CustomGameEvent`を継承している必要があります。`CustomGameEvent#onEvent`にイベントの中身を書きます。
  - `class` (string) \- クラスパスです。