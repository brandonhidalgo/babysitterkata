# Babysitter Kata
As a babysitter, I want to know how much I will get paid for one night of work.

## Instructions
### Before runing any of the commands make sure you are in the root directory of the repo (~/babysitterkata)
To build an apk run:
>`gradlew assembleDebug`

To install the apk to a device/emulator run:
>`gradlew installDebug`

To run all unit tests:
>`gradlew testDebug`

## ADHOC Manual Test Cases

#### What if babysitter starts before bedtime and ends after midnight?

  *Params*
  > BT - 23
  > ET - 25
  > ST - 22

  *Expected Result*
  > 12 + 8 + 16 = 36

#### What if babysitter starts before bedtime and ends before midnight?

  *Params*
  > BT - 20
  > ET - 23
  > ST - 18

  *Expected Result*
  > 24 + 24 = 48

#### What if babysitter starts before bedtime and ends at midnight?

  *Params*
  > BT - 20
  > ET - 24
  > ST - 18

  *Expected Result*
  > 24 + 32 = 56

#### What if babysitter starts after bedtime and ends after midnight?

  *Params*
  > BT - 22
  > ET - 25
  > ST - 23

  *Expected Result*
  > 8 + 16 = 24

#### What if babysitter starts after bedtime and ends before midnight?

  *Params*
  > BT - 20
  > ET - 23
  > ST - 21

  *Expected Result*
  > 16

#### What if babysitter starts after bedtime and ends at midnight?

  *Params*
  > BT - 20
  > ET - 24
  > ST - 21

  *Expected Result*
  > 24

#### What if babysitter starts at bedtime and ends before midnight?

  *Params*
  > BT - 20
  > ET - 23
  > ST - 20

  *Expected Result*
  > 24

#### What if babysitter starts at bedtime and ends after midnight?

  *Params*
  > BT - 20
  > ET - 26
  > ST - 20

  *Expected Result*
  > 32 + 32 = 64

#### What if babysitter starts at bedtime and ends at midnight?

  *Params*
  > BT - 20
  > ET - 24
  > ST - 20

  *Expected Result*
  > 32

#### What if babysitter starts after midnight?

  *Params*
  > BT - 20
  > ET - 28
  > ST - 25

  *Expected Result*
  > 48

#### What if babysittyer starts at midnight?

  *Params*
  > BT - 20
  > ET - 28
  > ST - 24

  *Expected Result*
  > 64

#### *Finally* HAPPY PATH! What if babysitter comes in before bedtime and sits through bedtime till after midnight?

  *Params*
  > BT - 20
  > ET - 27
  > ST - 17

  *Expected Result*
  > 36 + 32 + 48 = 116
