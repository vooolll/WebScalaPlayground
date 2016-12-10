package filters

import com.google.inject.Inject
import play.api.http.DefaultHttpFilters

class Filters @Inject() (log: LoggingFilter) extends DefaultHttpFilters(log)