cba Type Class Initial Design
---

From the initial thinking laid out in [/journal.md](/journal.md) file, this is the organization of the cba type class structure:

```
CbaType (abstract type)                     --Done
 +- CbaReal (abstract type)                 --Done
 |   +- CbaFixedPoint (abstract type)       --Done
 |   |   +- CbaDecimal                      --Done
 |   +- CbaFloatingPoint (abstract type)    --Done
 |       +- CbaFloat                        --Done
 |       +- CbaDouble                       --Done
 +- CbaInteger (abstract type)              --Done
 |   +- CbaTinyInt                          --Done
 |   |   +- CbaBoolean                      --Done
 |   +- CbaSmallInt                         --Done
 |   +- CbaMediumInt                        --Done
 |   +- CbaInteger                          --Done
 |   +- CbaBigInt                           --Done
 +- CbaCharType (abstract type)
 |   +- CbaChar
 |   +- CbaVarChar
 |   +- CbaTinyText
 |   +- CbaText
 |   +- CbaMediumText
 |   +- CbaLongText
 +- CbaBinaryType (abstract type)
 |   +- CbaBit
 |   +- CbaBinary
 |   +- CbaVarBinary
 |   +- CbaTinyBlob
 |   +- CbaBlob
 |   +- CbaMediumBlob
 |   +- CbaLongBlob
 +- CbaTemporalType (abstract type)
     +- CbaDate
     +- CbaDateTime
     +- CbaTime
     +- CbaTimestamp
```

So, this illustrates the hierarchy.
