cba Type Class Initial Design
---

From the initial thinking laid out in [/journal.md](/journal.md) file, this is the organization of the cba type class structure:

```
CbaType (abstract type)
 +- CbaReal (abstract type)
 |   +- CbaFixedPoint (abstract type)
 |   |   +- CbaDecimal
 |   +- CbaFloatingPoint (abstract type)
 |       +- CbaFloat
 |       +- CbaDouble
 +- CbaInteger (abstract type)
 |   +- CbaTinyInt --Done
 |   |   +- CbaBoolean --Done
 |   +- CbaSmallInt --Done
 |   +- CbaMediumInt --Done
 |   +- CbaInteger
 |   +- CbaBigInt
 +- CbaCharType (abstract type)
 |   +- CbaChar
 |   +- CbaVarChar
 |   +- CbaTinyText
 |   +- CbaText
 |   +- CbaMediumText
 |   +- CbaLongText
 +- CbaBinary (abstract type)
 |   +- CbaBit
 |   +- CbaBinary
 |   +- CbaVarBinary
 |   +- CbaTinyBlob
 |   +- CbaBlob
 |   +- CbaMediumBlob
 |   +- CbaLongBlob
 +- CbaTemporal (abstract type)
     +- CbaDate
     +- CbaDateTime
     +- CbaTimestamp
     +- CbaTime
```

So, this illustrates the hierarchy.
